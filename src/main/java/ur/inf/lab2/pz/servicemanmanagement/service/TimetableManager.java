package ur.inf.lab2.pz.servicemanmanagement.service;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.Timetable;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.TimetableTask;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.UnallocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

interface TimetableDatasource {
    Set<TimetableTask> getTasksByDateRange(Long leaderId, DateRange peroidOfTime);

    Set<TimetableTask> getUnallocatedTasks();
}


class ManagerTimetable implements Timetable {
    private static final String DESCRIPTION_PATTERN = "{id} / {tag} /\n{description}";
    private static final String[] GLOBAL_STYLE_CLASSES = {"global-font"};
    private static final String EMPTY_TASK_ID = "empty-task-id";
    private static final String TASK_STYLE_CLASS_NAME = "task";
    private static final String TASK_SELECTED_STYLE_CLASSNAME = "task-selected";
    private static final String EMPTY_TASK_CLASS_NAME = "empty-task";
    private DateRange dateRange;
    private Agenda.AppointmentGroup appointmentGroup;
    private Agenda.AppointmentGroup appointmentSelectedGroup;
    private Agenda.AppointmentGroup appointmentEmptyGroup;


    private Agenda agenda;

    public ManagerTimetable(DateRange dateRange, TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        this.dateRange = dateRange;
        initGroups();
        initAgenda(unallocatedTaskTable);
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String rawDate = format.format(date);

        return LocalDate.parse(rawDate).atTime(0, 0);
    }

    private void initAgenda(TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        agenda = new Agenda();
        agenda.displayedLocalDateTime().set(toLocalDateTime(dateRange.getFrom()));


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

    private void allowDetachTask(TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        agenda.appointments().addListener((ListChangeListener<? super Agenda.Appointment>) changeListener -> {
            changeListener.next();
            if (changeListener.wasRemoved()) {
                List<? extends Agenda.Appointment> detachedAppointments = changeListener.getRemoved();
                Set<UnallocatedTask> detachedTasks = transformAppointmentsToUnallocatedTasks(detachedAppointments);

                addDetachedTasksToTable(detachedTasks, unallocatedTaskTable);
            }
        });
    }

    private void addDetachedTasksToTable(Set<UnallocatedTask> detachedTasks, TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        List<TreeItem<UnallocatedTask>> newTableItems = detachedTasks.stream()
                .map(TreeItem::new)
                .collect(Collectors.toList());

        unallocatedTaskTable.getRoot().getChildren().addAll(newTableItems);
    }

    private void allowAllocateUnallocatedTaskToEmptyTask(TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        agenda.setActionCallback(appointment -> {
            if (appointment.getDescription().equals(EMPTY_TASK_ID)) {
                TreeItem<UnallocatedTask> selectedItem = unallocatedTaskTable.getSelectionModel().getSelectedItem();

                if (selectedItem != null) {
                    UnallocatedTask unallocatedTask = selectedItem.getValue();
                    allocateTaskToAppointment(unallocatedTask, appointment);
                    removeItemFromTreeTableView(selectedItem, unallocatedTaskTable);
                    agenda.refresh();
                }

            }

            return null;
        });
    }

    private void removeItemFromTreeTableView(TreeItem<UnallocatedTask> selectedItem,
                                             TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        unallocatedTaskTable.getSelectionModel().clearSelection();
        unallocatedTaskTable.getRoot().getChildren().remove(selectedItem);
    }

    private void allocateTaskToAppointment(UnallocatedTask unallocatedTask, Agenda.Appointment appointment) {
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
    public void loadTasks(Set<TimetableTask> tasks) {
        if (tasks == null)
            throw new NullPointerException();

        agenda.appointments().addAll(transformTasksToAppointments(tasks));
    }

    @Override
    public Agenda generate() {
        Agenda agenda = new Agenda();
//        agenda.displayedLocalDateTime().set(toLocalDateTime(dateRange.getFrom()));
//        agenda.appointments().addAll(transformTasksToAppointments());
//
//
//        setStyleOnSelected(agenda);
//        allowCreatingTasksByDrag(agenda);
//        agenda.getStyleClass().addAll(GLOBAL_STYLE_CLASSES);
        return agenda;
    }

    @Override
    public Control getView() {
        return agenda;
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

    private Collection<? extends Agenda.Appointment> transformTasksToAppointments(Set<TimetableTask> tasks) {
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

    private Set<UnallocatedTask> transformAppointmentsToUnallocatedTasks(List<? extends Agenda.Appointment> detachedAppointments) {
        return detachedAppointments.stream()
                .map(appointment -> {
                    String summary = appointment.getSummary();

                    String taskId = appointment.getDescription();
                    String tagFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{tag}", summary, '/');
                    String descFromSummary = StringUtils.reverseFormat(DESCRIPTION_PATTERN, "{description}", summary, '/');

                    return new UnallocatedTask(taskId, tagFromSummary, descFromSummary);
                }).collect(Collectors.toSet());
    }

    private String prepareTaskSummary(TimetableTask task) {
        return DESCRIPTION_PATTERN.replace("{id}", task.getId())
                .replace("{tag}", task.getTag())
                .replace("{description}", task.getDescription());
    }

    public DateRange getDateRange() {
        return dateRange;
    }

}


@Service
public class TimetableManager {

    @Autowired
    private TimetableDatasource datasource;

    public static DateRange getWeekDateRangeByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date actualWeekMondaysDate = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);

        Date actualWeekSundaysDate = cal.getTime();

        return new DateRange(actualWeekMondaysDate, actualWeekSundaysDate);
    }

    public Agenda load(Long leaderId) {
        Date now = new Date();
        Timetable timetable = new ManagerTimetable(getWeekDateRangeByDate(now), null);

        Set<TimetableTask> tasksFromActualWeek = datasource.getTasksByDateRange(leaderId, timetable.getDateRange());
        timetable.loadTasks(tasksFromActualWeek);
        return timetable.generate();
//
//        Agenda agenda = new Agenda();
//        agenda.displayedLocalDateTime().set(LocalDate.parse("2019-05-01").atTime(2, 10));
//        Agenda.AppointmentGroupImpl taskGroup = new Agenda.AppointmentGroupImpl().withStyleClass("task");
//        Agenda.AppointmentImplLocal task = new Agenda.AppointmentImplLocal()
//                .withStartLocalDateTime(LocalDate.parse("2019-05-01").atTime(4, 00))
//                .withEndLocalDateTime(LocalDate.parse("2019-05-01").atTime(15, 30))
//                .withDescription("It's time")
//                .withSummary("TO JEST ZADANIE KOLEŻKO")
//                .withAppointmentGroup(taskGroup);
//
//        agenda.appointments().addAll(
//                task // you should use a map of AppointmentGroups
//        );
//
//        agenda.setOnDragDropped(val -> {
//            System.out.println(val.getX());
//        });
//
//        agenda.newAppointmentCallbackProperty().set( (localDateTimeRange) -> {
//            return new Agenda.AppointmentImplLocal()
//                    .withStartLocalDateTime(localDateTimeRange.getStartLocalDateTime())
//                    .withEndLocalDateTime(localDateTimeRange.getEndLocalDateTime())
//                    .withAppointmentGroup(new Agenda.AppointmentGroupImpl().withStyleClass("task")); // it is better to have a map of appointment groups to get from
//        });
//
////        agenda.setStyle("-fx-font-size: 40");
//        agenda.getStyleClass().add("global-font");
//        return agenda;
//        viewManager.getStae().getScene().getStylesheets().forEach(stylesheet -> agenda.getStylesheets().add(stylesheet));
    }

    public TreeItem<UnallocatedTask> getUnallocatedTasksAsTreeItem() {
        Set<UnallocatedTask> unallocatedTasks = datasource.getUnallocatedTasks().stream()
                .map(UnallocatedTask::new)
                .collect(Collectors.toSet());

        ObservableList<UnallocatedTask> unallocatedTasksList = FXCollections.observableArrayList(unallocatedTasks);
        return new RecursiveTreeItem<>(unallocatedTasksList, RecursiveTreeObject::getChildren);
    }

    public Timetable createTimetable(TreeTableView<UnallocatedTask> unallocatedTaskTable) {
        Date now = new Date();
        return new ManagerTimetable(getWeekDateRangeByDate(now), unallocatedTaskTable);
    }

    public Set<TimetableTask> getTasksFromActualWeek(Long leaderId) {
        Date now = new Date();
        return datasource.getTasksByDateRange(leaderId, getWeekDateRangeByDate(now));
    }
}
