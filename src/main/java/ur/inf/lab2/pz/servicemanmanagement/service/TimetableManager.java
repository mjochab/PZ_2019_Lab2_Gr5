package ur.inf.lab2.pz.servicemanmanagement.service;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import jfxtras.scene.control.agenda.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.*;
import ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils.toLocalDateTime;

interface TimetableDatasource {

    Set<AllocatedTask> getAllocatedTasks(Long leaderId);

    Set<UnallocatedTask> getUnallocatedTasks();

    void saveAllocated(Collection<AllocatedTask> allocatedTasks);

    void saveUnallocated(Collection<UnallocatedTask> unallocatedTasks);

    List<GroupData> getGroups();
}

interface AllocatedTaskRaportPrinter {
    void print(AllocatedTask taskToPrint, String absolutePath);
}

class ManagerTimetable implements Timetable {
    private static final String DESCRIPTION_PATTERN = "{id} / {tag} /\n{description}";
    private static final String[] GLOBAL_STYLE_CLASSES = {"global-font"};
    private static final String EMPTY_TASK_ID = "empty-task-id";
    private static final String TASK_STYLE_CLASS_NAME = "task";
    private static final String TASK_SELECTED_STYLE_CLASSNAME = "task-selected";
    private static final String EMPTY_TASK_CLASS_NAME = "empty-task";
    private static final String DONE_TASK_CLASS_NAME = "done-task";
    private DateRange weekDateRange;
    private Agenda.AppointmentGroup appointmentGroup;
    private Agenda.AppointmentGroup appointmentDoneGroup;
    private Agenda.AppointmentGroup appointmentSelectedGroup;
    private Agenda.AppointmentGroup appointmentEmptyGroup;

    private Agenda agenda;
    private AllocatedTaskRaportPrinter raportPrinter;
    private Map<String, ClientData> clientsData;
    private Map<String, TaskState> tasksStates;

    public ManagerTimetable(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable,
                            TimetableTaskEditDialogData editTaskDialogData,
                            AllocatedTaskRaportPrinter printer) {
        Date now = new Date();
        this.weekDateRange = DateUtils.getWeekDateRangeByDate(now);
        initGroups();
        initAgenda(unallocatedTaskTable, editTaskDialogData);

        raportPrinter = printer;
        clientsData = new HashMap<>();
        tasksStates = new HashMap<>();
    }


    private void initAgenda(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable, TimetableTaskEditDialogData editTaskDialogData) {
        agenda = new Agenda();
        agenda.displayedLocalDateTime().set(toLocalDateTime(weekDateRange.getFrom()));

        initTaskEditDialog(editTaskDialogData);

        setStyleOnSelected();
        allowCreatingTasksByDrag();
        allowAllocateUnallocatedTaskToEmptyTask(unallocatedTaskTable);
        allowDetachTask(unallocatedTaskTable);
        agenda.getStyleClass().addAll(GLOBAL_STYLE_CLASSES);
    }

    private void initTaskEditDialog(TimetableTaskEditDialogData editTaskDialogData) {
        agenda.setEditAppointmentCallback(app -> {

            if (isEmptyTask(app)) {
                agenda.appointments().remove(app);
            } else {
                JFXDialog editTaskDialog = prepareEditTaskDialog(editTaskDialogData.getStackPane(), editTaskDialogData.getView());
                setDialogActions(app, editTaskDialog, editTaskDialogData);
                setTaskData(app, editTaskDialogData);
                editTaskDialog.show();
            }

            return null;
        });
    }

    private boolean isEmptyTask(Agenda.Appointment app) {
        return app.getDescription().equals(EMPTY_TASK_ID);
    }

    private void setTaskData(Agenda.Appointment app, TimetableTaskEditDialogData editTaskDialogData) {
        AllocatedTask task = transformAppointmentToTask(app);
        editTaskDialogData.setTaskId(task.getId());
        editTaskDialogData.setTaskTag(task.getTag());
        editTaskDialogData.setTaskDescription(task.getDescription());
        editTaskDialogData.setDateTimeFrom(task.getDateTimeFrom());
        editTaskDialogData.setDateTimeTo(task.getDateTimeTo());
        editTaskDialogData.setWholeDay(task.isWholeDayTask());
        editTaskDialogData.setClientData(task.getClientData());
        editTaskDialogData.setState(task.getState().getName());
    }

    private void setDialogActions(Agenda.Appointment appointment, JFXDialog editTaskDialog, TimetableTaskEditDialogData editTaskDialogData) {
        Node exitNode = editTaskDialogData.getExitNode();
        exitNode.setOnMouseClicked(event -> editTaskDialog.close());

        Node printNode = editTaskDialogData.getPrintNode();
        printNode.setOnMouseClicked(event -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Wybierz katalog na raport");
            File selectedDirectory = chooser.showDialog(null);

            if (selectedDirectory != null)
                raportPrinter.print(transformAppointmentToTask(appointment), selectedDirectory.getAbsolutePath());
        });

        if (isDoneTask(appointment)) {
            editTaskDialogData.disableDetachNode();
            editTaskDialogData.disableDescriptionNode();
            editTaskDialogData.disableDateFromNode();
            editTaskDialogData.disableTimeFromNode();
            editTaskDialogData.disableDateToNode();
            editTaskDialogData.disableTimeToNode();
            editTaskDialogData.disableWholeDayNode();
            editTaskDialogData.disableSaveNode();
        } else {
            editTaskDialogData.getDetachNode()
                    .setOnMouseClicked(event -> {
                        agenda.appointments().remove(appointment);
                        editTaskDialog.close();
                    });

            editTaskDialogData.getSaveNode()
                    .setOnMouseClicked(event -> {
                        String newDescription = editTaskDialogData.getTaskDescription();
                        updateAppointmentDescription(appointment, newDescription);

                        LocalDateTime dateTimeFrom = editTaskDialogData.getDateTimeFrom();
                        if (editTaskDialogData.isWholeDayTask())
                            setAppointmentAsWholeDay(appointment, dateTimeFrom);
                        else {
                            LocalDateTime dateTimeTo = editTaskDialogData.getDateTimeTo();
                            updateAppointmentDuration(appointment, dateTimeTo, dateTimeFrom);
                        }

                        agenda.refresh();
                        editTaskDialog.close();
                    });
        }
    }

    private void updateAppointmentDuration(Agenda.Appointment appointment, LocalDateTime dateTimeTo, LocalDateTime dateTimeFrom) {
        appointment.setStartLocalDateTime(dateTimeFrom);
        appointment.setEndLocalDateTime(dateTimeTo);
        appointment.setWholeDay(false);
    }

    private void setAppointmentAsWholeDay(Agenda.Appointment appointment, LocalDateTime dateTimeFrom) {
        appointment.setStartLocalDateTime(dateTimeFrom);
        appointment.setEndLocalDateTime(dateTimeFrom);
        appointment.setWholeDay(true);
    }

    private void updateAppointmentDescription(Agenda.Appointment appointment, String newDescription) {
        AllocatedTask task = transformAppointmentToTask(appointment);
        String newTaskSummary = prepareTaskSummary(task.getId(), task.getTag(), newDescription);
        appointment.setSummary(newTaskSummary);
    }


    private JFXDialog prepareEditTaskDialog(StackPane stackPane, Node view) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setBody(view);

        return new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
    }

    private void allowDetachTask(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        agenda.appointments().addListener((ListChangeListener<? super Agenda.Appointment>) changeListener -> {
            changeListener.next();
            if (changeListener.wasRemoved()) {
                List<? extends Agenda.Appointment> detachedAppointments = changeListener.getRemoved();
                List<? extends Agenda.Appointment> notEmptyDetachedAppointments = detachedAppointments.stream()
                        .filter(app -> !isEmptyTask(app))
                        .collect(Collectors.toList());
                Set<UnallocatedTaskTableItem> detachedTasks = transformAppointmentsToUnallocatedTasks(notEmptyDetachedAppointments);

                addDetachedTasksToTable(detachedTasks, unallocatedTaskTable);
            }
        });
    }

    private void addDetachedTasksToTable(Set<UnallocatedTaskTableItem> detachedTasks, TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        List<TreeItem<UnallocatedTaskTableItem>> newTableItems = detachedTasks.stream()
                .map(TreeItem::new)
                .collect(Collectors.toList());

        unallocatedTaskTable.getRoot().getChildren().addAll(newTableItems);
    }

    private void allowAllocateUnallocatedTaskToEmptyTask(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        agenda.setActionCallback(appointment -> {
            if (isEmptyTask(appointment)) {
                TreeItem<UnallocatedTaskTableItem> selectedItem = unallocatedTaskTable.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    UnallocatedTaskTableItem unallocatedTask = selectedItem.getValue();

                    saveClientData(unallocatedTask.getId(), unallocatedTask.getClientData());
                    System.out.println(unallocatedTask.getId() + " " + unallocatedTask.getClientData());
                    allocateTaskToAppointment(unallocatedTask, appointment);
                    removeItemFromTreeTableView(selectedItem, unallocatedTaskTable);
                    agenda.refresh();
                }

            }

            return null;
        });
    }

    private void saveClientData(String id, ClientData clientData) {
        clientsData.put(id, clientData);
    }

    private void removeItemFromTreeTableView(TreeItem<UnallocatedTaskTableItem> selectedItem,
                                             TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        unallocatedTaskTable.getSelectionModel().clearSelection();
        unallocatedTaskTable.getRoot().getChildren().remove(selectedItem);
    }

    private void allocateTaskToAppointment(UnallocatedTaskTableItem unallocatedTask, Agenda.Appointment appointment) {
        appointment.setAppointmentGroup(appointmentGroup);
        appointment.setSummary(prepareTaskSummary(unallocatedTask));
        appointment.setDescription(unallocatedTask.getId());
    }

    private void initGroups() {
        appointmentGroup = new Agenda.AppointmentGroupImpl().withStyleClass(TASK_STYLE_CLASS_NAME);
        appointmentSelectedGroup = new Agenda.AppointmentGroupImpl().withStyleClass(TASK_SELECTED_STYLE_CLASSNAME);
        appointmentEmptyGroup = new Agenda.AppointmentGroupImpl().withStyleClass(EMPTY_TASK_CLASS_NAME);
        appointmentDoneGroup = new Agenda.AppointmentGroupImpl().withStyleClass(DONE_TASK_CLASS_NAME);
    }

    @Override
    public void loadTasks(Collection<AllocatedTask> tasks) {
        if (tasks == null)
            throw new NullPointerException();

        Collection<? extends Agenda.Appointment> c = transformTasksToAppointments(tasks);
        tasks.forEach(task -> {
            saveClientData(task.getId(), task.getClientData());
            saveTaskState(task.getId(), task.getState());
        });
        agenda.appointments().addAll(c);
    }

    private void saveTaskState(String id, TaskState state) {
        tasksStates.put(id, state);
    }

    @Override
    public Control getView() {
        return agenda;
    }

    @Override
    public Set<AllocatedTask> getAllocatedTasks() {
        return agenda.appointments().stream()
                .filter(appointment -> !isEmptyTask(appointment) && !isDoneTask(appointment))
                .map(this::transformAppointmentToTask)
                .collect(Collectors.toSet());
    }

    @Override
    public void clear() {
        agenda.appointments().clear();
    }

    @Override
    public DateRange nextWeek() {
        Date actualWeekSunday = weekDateRange.getTo();
        Date nextWeekMonday = DateUtils.plusDays(actualWeekSunday, 1);
        agenda.displayedLocalDateTime().set(toLocalDateTime(nextWeekMonday));

        weekDateRange = DateUtils.getWeekDateRangeByDate(nextWeekMonday);
        return weekDateRange;
    }

    @Override
    public DateRange prevWeek() {
        Date actualWeekMonday = weekDateRange.getFrom();
        Date lastWeekSunday = DateUtils.minusDays(actualWeekMonday, 1);
        agenda.displayedLocalDateTime().set(toLocalDateTime(lastWeekSunday));

        weekDateRange = DateUtils.getWeekDateRangeByDate(lastWeekSunday);
        return weekDateRange;
    }

    private AllocatedTask transformAppointmentToTask(Agenda.Appointment appointment) {
        String summary = appointment.getSummary();

        String taskId = appointment.getDescription();
        String tagFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{tag}", summary, '/');
        String descFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{description}", summary, '/');
        LocalDateTime startDateTime = appointment.getStartLocalDateTime();
        LocalDateTime endDateTime = appointment.getEndLocalDateTime();
        boolean isWholeDay = appointment.isWholeDay();
        ClientData clientData = getClientData(taskId);
        TaskState state = getTaskState(taskId);

        return new TimetableTaskData(
                taskId,
                tagFromSummary,
                descFromSummary,
                startDateTime,
                endDateTime,
                isWholeDay,
                clientData,
                state);
    }

    private TaskState getTaskState(String taskId) {
        return tasksStates.get(taskId);
    }

    private ClientData getClientData(String taskId) {
        return clientsData.get(taskId);
    }

    private void setStyleOnSelected() {
        agenda.selectedAppointments().addListener((ListChangeListener<Agenda.Appointment>) changeListener -> {
            changeListener.next();
            if (changeListener.wasAdded()) {
                changeListener.getList().forEach(app -> app.setAppointmentGroup(appointmentSelectedGroup));
            }

            if (changeListener.wasRemoved()) {
                changeListener.getRemoved().forEach(app -> {

                    if (isEmptyTask(app))
                        app.setAppointmentGroup(appointmentEmptyGroup);
                    else if (isDoneTask(app))
                        app.setAppointmentGroup(appointmentDoneGroup);
                    else
                        app.setAppointmentGroup(appointmentGroup);

                });
            }

            agenda.refresh();
        });
    }

    private boolean isDoneTask(Agenda.Appointment app) {
        String id = app.getDescription();
        return tasksStates.get(id).equals(TaskState.DONE);
    }

    private void allowCreatingTasksByDrag() {
        agenda.newAppointmentCallbackProperty().set((dateTimeRange) ->
                new Agenda.AppointmentImplLocal()
                        .withStartLocalDateTime(dateTimeRange.getStartLocalDateTime())
                        .withEndLocalDateTime(dateTimeRange.getEndLocalDateTime())
                        .withSummary("PUSTE ZADANIE \nZaznacz zadanie do przypięcia w powyższej tabeli oraz kliknij dwukrotnie tutaj, aby przypiąć zadanie do hamonogramu")
                        .withDescription(EMPTY_TASK_ID)
                        .withAppointmentGroup(appointmentEmptyGroup)
        );
    }

    private Collection<? extends Agenda.Appointment> transformTasksToAppointments(Collection<AllocatedTask> tasks) {
        return tasks.stream()
                .map(rawTask -> {
                    TaskState state = rawTask.getState();

                    Agenda.AppointmentGroup groupForTask;
                    if (state.equals(TaskState.TODO)) {
                        groupForTask = appointmentGroup;
                    } else
                        groupForTask = appointmentDoneGroup;

                    return new Agenda.AppointmentImplLocal()
                            .withStartLocalDateTime(rawTask.getDateTimeFrom())
                            .withEndLocalDateTime(rawTask.getDateTimeTo())
                            .withDescription(rawTask.getId())
                            .withSummary(prepareTaskSummary(rawTask))
                            .withAppointmentGroup(groupForTask)
                            .withWholeDay(rawTask.isWholeDayTask());
                }).collect(Collectors.toList());
    }

    private Set<UnallocatedTaskTableItem> transformAppointmentsToUnallocatedTasks(List<? extends Agenda.Appointment> detachedAppointments) {
        return detachedAppointments.stream()
                .map(appointment -> {
                    String summary = appointment.getSummary();

                    String taskId = appointment.getDescription();
                    String tagFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{tag}", summary, '/');
                    String descFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{description}", summary, '/');
                    ClientData clientData = getClientData(taskId);

                    return new UnallocatedTaskTableItem(taskId, tagFromSummary, descFromSummary, clientData);
                }).collect(Collectors.toSet());
    }

    private String prepareTaskSummary(TimetableTask task) {
        return prepareTaskSummary(task.getId(), task.getTag(), task.getDescription());
    }

    private String prepareTaskSummary(String id, String tag, String description) {
        return DESCRIPTION_PATTERN.replace("{id}", id)
                .replace("{tag}", tag)
                .replace("{description}", description);
    }


}


@Service
public class TimetableManager {

    @Autowired
    private TimetableDatasource datasource;


    public TreeItem<UnallocatedTaskTableItem> getUnallocatedTasksAsTreeItem() {
        Set<UnallocatedTaskTableItem> unallocatedTasks = datasource.getUnallocatedTasks().stream()
                .map(UnallocatedTaskTableItem::new)
                .collect(Collectors.toSet());

        ObservableList<UnallocatedTaskTableItem> unallocatedTasksList = FXCollections.observableArrayList(unallocatedTasks);
        return new RecursiveTreeItem<>(unallocatedTasksList, RecursiveTreeObject::getChildren);
    }

    public Timetable createTimetable(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable, StackPane rootStackPane, Parent editTaskDialogBody) {
        TimetableTaskEditDialogData dialogData = new ManagerTaskEditDialogData(editTaskDialogBody, rootStackPane);

        return new ManagerTimetable(unallocatedTaskTable, dialogData, new MockPrinter());
    }

    public Set<AllocatedTask> getAllAllocatedTasks(Long leaderId) {
        return datasource.getAllocatedTasks(leaderId);
    }

    public void save(Set<AllocatedTask> allocatedTasks, Set<UnallocatedTask> unallocatedTasks) {
        datasource.saveAllocated(allocatedTasks);
        datasource.saveUnallocated(unallocatedTasks);
    }

    public List<GroupData> getAllGroups() {
        return datasource.getGroups();
    }

    private class MockPrinter implements AllocatedTaskRaportPrinter {

        @Override
        public void print(AllocatedTask taskToPrint, String absolutePath) {
            System.out.println("Save task: " + taskToPrint + " to path: " + absolutePath);
        }
    }

    private class ManagerTaskEditDialogData implements TimetableTaskEditDialogData {

        private static final String EXIT_NODE_ID = "exit";
        private static final String TASK_ID_NODE_ID = "taskId";
        private static final String TASK_TAG_NODE_ID = "taskTag";
        private static final String DESCRIPTION_NODE_ID = "taskDescription";
        private static final String DATE_FROM_NODE_ID = "dateFrom";
        private static final String TIME_FROM_NODE_ID = "timeFrom";
        private static final String DATE_TO_NODE_ID = "dateTo";
        private static final String TIME_TO_NODE_ID = "timeTo";
        private static final String WHOLE_DAY_NODE_ID = "wholeDay";
        private static final String DETACH_TASK_NODE_ID = "detachTask";
        private static final String SAVE_TASK_NODE_ID = "saveTask";
        private static final String PRINT_NODE_ID = "print";
        private static final String CLIENT_FIRSTNAME_LABEL_ID = "clientFirstname";
        private static final String CLIENT_SURNAME_LABEL_ID = "clientSurname";
        private static final String CLIENT_PHONE_NUMBER_LABEL_ID = "clientPhoneNumber";
        private static final String CLIENT_STREET_LABEL_ID = "clientStreet";
        private static final String CLIENT_HOUSE_NUMBER_LABEL_ID = "clientHouseNumber";
        private static final String CLIENT_FLAT_NUMBER_LABEL_ID = "clientFlatNumber";
        private static final String CLIENT_CITY_LABEL_ID = "clientCity";
        private static final String STATE_BUTTON_ID = "stateButton";
        private Parent dialogBody;
        private StackPane stackPaneForDialog;

        public ManagerTaskEditDialogData(Parent dialogBody, StackPane stackPane) {
            this.dialogBody = dialogBody;
            this.stackPaneForDialog = stackPane;

            initTaskDurationChanges();
        }

        private void initTaskDurationChanges() {
            CheckBox wholeDayCheckbox = (CheckBox) findNodeInDialogBody(WHOLE_DAY_NODE_ID);
            wholeDayCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    disableNodeById(DATE_TO_NODE_ID);
                    disableNodeById(TIME_TO_NODE_ID);
                    disableNodeById(TIME_FROM_NODE_ID);
                } else {
                    enableNodeById(DATE_TO_NODE_ID);
                    enableNodeById(TIME_TO_NODE_ID);
                    enableNodeById(TIME_FROM_NODE_ID);
                }
            });

        }

        private void enableNodeById(String timeFromNodeId) {
            findNodeInDialogBody(timeFromNodeId).setDisable(false);
        }

        private void disableNodeById(String dateToNodeId) {
            findNodeInDialogBody(dateToNodeId).setDisable(true);
        }

        @Override
        public StackPane getStackPane() {
            return stackPaneForDialog;
        }

        @Override
        public Node getView() {
            return dialogBody;
        }

        @Override
        public Node getExitNode() {
            return findNodeInDialogBody(EXIT_NODE_ID);
        }

        private Node findNodeInDialogBody(String nodeId) {
            return dialogBody.lookup("#" + nodeId);
        }

        @Override
        public void setTaskId(String id) {
            ((Label) findNodeInDialogBody(TASK_ID_NODE_ID)).setText(id);
        }

        @Override
        public void setTaskTag(String tag) {
            ((Label) findNodeInDialogBody(TASK_TAG_NODE_ID)).setText(tag);
        }

        @Override
        public String getTaskDescription() {
            return ((TextArea) findNodeInDialogBody(DESCRIPTION_NODE_ID)).getText();
        }

        @Override
        public void setTaskDescription(String description) {
            ((TextArea) findNodeInDialogBody(DESCRIPTION_NODE_ID)).setText(description);
        }

        @Override
        public LocalDateTime getDateTimeFrom() {
            LocalDate date = ((DatePicker) findNodeInDialogBody(DATE_FROM_NODE_ID)).getValue();
            LocalTime time = ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_FROM_NODE_ID)).getValue();
            return LocalDateTime.of(date, time);
        }

        @Override
        public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
            ((DatePicker) findNodeInDialogBody(DATE_FROM_NODE_ID)).setValue(dateTimeFrom.toLocalDate());
            ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_FROM_NODE_ID)).setValue(dateTimeFrom.toLocalTime());
        }

        @Override
        public LocalDateTime getDateTimeTo() {
            LocalDate date = ((DatePicker) findNodeInDialogBody(DATE_TO_NODE_ID)).getValue();
            LocalTime time = ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_TO_NODE_ID)).getValue();
            return LocalDateTime.of(date, time);
        }

        @Override
        public void setDateTimeTo(LocalDateTime dateTimeTo) {
            ((DatePicker) findNodeInDialogBody(DATE_TO_NODE_ID)).setValue(dateTimeTo.toLocalDate());
            ((ComboBoxBase<LocalTime>) findNodeInDialogBody(TIME_TO_NODE_ID)).setValue(dateTimeTo.toLocalTime());
        }

        @Override
        public Node getPrintNode() {
            return findNodeInDialogBody(PRINT_NODE_ID);
        }

        @Override
        public void setClientData(ClientData clientData) {
            setLabelNode(CLIENT_FIRSTNAME_LABEL_ID, clientData.getFirstname());
            setLabelNode(CLIENT_SURNAME_LABEL_ID, clientData.getSurname());
            setLabelNode(CLIENT_PHONE_NUMBER_LABEL_ID, clientData.getPhoneNumber());
            setLabelNode(CLIENT_STREET_LABEL_ID, clientData.getStreet());
            setLabelNode(CLIENT_HOUSE_NUMBER_LABEL_ID, clientData.getHouseNumber());
            setLabelNode(CLIENT_FLAT_NUMBER_LABEL_ID, clientData.getFlatNumber());
            setLabelNode(CLIENT_CITY_LABEL_ID, clientData.getCity());
        }

        @Override
        public void setState(String state) {
            ((Button) findNodeInDialogBody(STATE_BUTTON_ID)).setText(state);
        }

        @Override
        public void disableDetachNode() {
            getDetachNode().setDisable(true);
        }

        @Override
        public void disableDescriptionNode() {
            findNodeInDialogBody(DESCRIPTION_NODE_ID).setDisable(true);
        }

        @Override
        public void disableDateFromNode() {
            findNodeInDialogBody(DATE_FROM_NODE_ID).setDisable(true);
        }

        @Override
        public void disableTimeFromNode() {
            findNodeInDialogBody(TIME_FROM_NODE_ID).setDisable(true);
        }

        @Override
        public void disableDateToNode() {
            findNodeInDialogBody(DATE_TO_NODE_ID).setDisable(true);
        }

        @Override
        public void disableTimeToNode() {
            findNodeInDialogBody(TIME_TO_NODE_ID).setDisable(true);
        }

        @Override
        public void disableWholeDayNode() {
            findNodeInDialogBody(WHOLE_DAY_NODE_ID).setDisable(true);
        }

        @Override
        public void disableSaveNode() {
            findNodeInDialogBody(SAVE_TASK_NODE_ID).setDisable(true);
        }

        private void setLabelNode(String nodeId, String value) {
            ((Label) findNodeInDialogBody(nodeId)).setText(value);
        }

        @Override
        public boolean isWholeDayTask() {
            return ((CheckBox) findNodeInDialogBody(WHOLE_DAY_NODE_ID)).isSelected();
        }

        @Override
        public void setWholeDay(boolean wholeDayTask) {
            ((CheckBox) findNodeInDialogBody(WHOLE_DAY_NODE_ID)).setSelected(wholeDayTask);
        }

        @Override
        public Node getDetachNode() {
            return findNodeInDialogBody(DETACH_TASK_NODE_ID);
        }

        @Override
        public Node getSaveNode() {
            return findNodeInDialogBody(SAVE_TASK_NODE_ID);
        }


    }
}
