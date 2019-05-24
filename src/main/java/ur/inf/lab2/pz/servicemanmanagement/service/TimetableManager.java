package ur.inf.lab2.pz.servicemanmanagement.service;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import jfxtras.scene.control.agenda.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.*;
import ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils.toLocalDateTime;

interface TimetableDatasource {

    Set<AllocatedTask> getAllocatedTasks(Long leaderId);

    Set<UnallocatedTask> getUnallocatedTasks();

    void saveAllocated(Collection<AllocatedTask> allocatedTasks);

    void saveUnallocated(Collection<UnallocatedTask> unallocatedTasks);
}


class ManagerTimetable implements Timetable {
    private static final String DESCRIPTION_PATTERN = "{id} / {tag} /\n{description}";
    private static final String[] GLOBAL_STYLE_CLASSES = {"global-font"};
    private static final String EMPTY_TASK_ID = "empty-task-id";
    private static final String TASK_STYLE_CLASS_NAME = "task";
    private static final String TASK_SELECTED_STYLE_CLASSNAME = "task-selected";
    private static final String EMPTY_TASK_CLASS_NAME = "empty-task";
    private DateRange weekDateRange;
    private Agenda.AppointmentGroup appointmentGroup;
    private Agenda.AppointmentGroup appointmentSelectedGroup;
    private Agenda.AppointmentGroup appointmentEmptyGroup;

    private Agenda agenda;

    public ManagerTimetable(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        Date now = new Date();
        this.weekDateRange = DateUtils.getWeekDateRangeByDate(now);
        initGroups();
        initAgenda(unallocatedTaskTable);
    }



    private void initAgenda(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        agenda = new Agenda();
        agenda.displayedLocalDateTime().set(toLocalDateTime(weekDateRange.getFrom()));

//        agenda.setEditAppointmentCallback(app -> { TODO custom edit dialog
//            final Stage dialog = new Stage();
//            dialog.initModality(Modality.APPLICATION_MODAL);
//            VBox dialogVbox = new VBox(20);
//            dialogVbox.getChildren().add(new Text("This is a Dialog"));
//            Scene dialogScene = new Scene(dialogVbox, 300, 200);
//            dialog.setScene(dialogScene);
//            dialog.show();
//
//            return null;
//        });


        setStyleOnSelected();
        allowCreatingTasksByDrag();
        allowAllocateUnallocatedTaskToEmptyTask(unallocatedTaskTable);
        allowDetachTask(unallocatedTaskTable);
        agenda.getStyleClass().addAll(GLOBAL_STYLE_CLASSES);
    }

    private void allowDetachTask(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        agenda.appointments().addListener((ListChangeListener<? super Agenda.Appointment>) changeListener -> {
            changeListener.next();
            if (changeListener.wasRemoved()) {
                List<? extends Agenda.Appointment> detachedAppointments = changeListener.getRemoved();
                List<? extends Agenda.Appointment> notEmptyDetachedAppointments = detachedAppointments.stream()
                        .filter(app -> !app.getDescription().equals(EMPTY_TASK_ID))
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
            if (appointment.getDescription().equals(EMPTY_TASK_ID)) {
                TreeItem<UnallocatedTaskTableItem> selectedItem = unallocatedTaskTable.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    UnallocatedTaskTableItem unallocatedTask = selectedItem.getValue();
                    allocateTaskToAppointment(unallocatedTask, appointment);
                    removeItemFromTreeTableView(selectedItem, unallocatedTaskTable);
                    agenda.refresh();
                }

            }

            return null;
        });
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
    }

    @Override
    public void loadTasks(Collection<AllocatedTask> tasks) {
        if (tasks == null)
            throw new NullPointerException();

        Collection<? extends Agenda.Appointment> c = transformTasksToAppointments(tasks);
        agenda.appointments().addAll(c);
    }

    @Override
    public Control getView() {
        return agenda;
    }

    @Override
    public Set<AllocatedTask> getAllocatedTasks() {
        return agenda.appointments().stream()
                .filter(appointment -> !EMPTY_TASK_ID.equals(appointment.getDescription()))
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

        return new TimetableTaskData(taskId, tagFromSummary, descFromSummary, startDateTime, endDateTime);
    }

    private void setStyleOnSelected() {
        agenda.selectedAppointments().addListener((ListChangeListener<Agenda.Appointment>) changeListener -> {
            changeListener.next();
            if (changeListener.wasAdded()) {
                changeListener.getList().forEach(app -> app.setAppointmentGroup(appointmentSelectedGroup));
            }

            if (changeListener.wasRemoved()) {
                changeListener.getRemoved().forEach(app -> {

                    if (EMPTY_TASK_ID.equals(app.getDescription()))
                        app.setAppointmentGroup(appointmentEmptyGroup);
                    else
                        app.setAppointmentGroup(appointmentGroup);

                });
            }

            agenda.refresh();
        });
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
                .map(rawTask ->
                        new Agenda.AppointmentImplLocal()
                                .withStartLocalDateTime(rawTask.getDateTimeFrom())
                                .withEndLocalDateTime(rawTask.getDateTimeTo())
                                .withDescription(rawTask.getId())
                                .withSummary(prepareTaskSummary(rawTask))
                                .withAppointmentGroup(appointmentGroup))
                .collect(Collectors.toList());
    }

    private Set<UnallocatedTaskTableItem> transformAppointmentsToUnallocatedTasks(List<? extends Agenda.Appointment> detachedAppointments) {
        return detachedAppointments.stream()
                .map(appointment -> {
                    String summary = appointment.getSummary();

                    String taskId = appointment.getDescription();
                    String tagFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{tag}", summary, '/');
                    String descFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{description}", summary, '/');

                    return new UnallocatedTaskTableItem(taskId, tagFromSummary, descFromSummary);
                }).collect(Collectors.toSet());
    }

    private String prepareTaskSummary(TimetableTask task) {
        return DESCRIPTION_PATTERN.replace("{id}", task.getId())
                .replace("{tag}", task.getTag())
                .replace("{description}", task.getDescription());
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

    public Timetable createTimetable(TreeTableView<UnallocatedTaskTableItem> unallocatedTaskTable) {
        return new ManagerTimetable(unallocatedTaskTable);
    }

    public Set<AllocatedTask> getAllAllocatedTasks(Long leaderId) {
        return datasource.getAllocatedTasks(leaderId);
    }

    public void save(Set<AllocatedTask> allocatedTasks, Set<UnallocatedTask> unallocatedTasks) {
        datasource.saveAllocated(allocatedTasks);
        datasource.saveUnallocated(unallocatedTasks);
    }
}
