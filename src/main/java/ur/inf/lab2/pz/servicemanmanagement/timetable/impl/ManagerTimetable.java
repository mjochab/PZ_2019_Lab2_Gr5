package ur.inf.lab2.pz.servicemanmanagement.timetable.impl;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import jfxtras.scene.control.agenda.Agenda;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.timetable.Timetable;
import ur.inf.lab2.pz.servicemanmanagement.timetable.TimetableTaskEditDialogData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.AllocatedTaskRaportPrinter;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.*;
import ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils.toLocalDateTime;

public class ManagerTimetable implements Timetable {
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
                editTaskDialogData.clean();
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
