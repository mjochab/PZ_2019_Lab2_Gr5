package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.services.TimetableManager;
import ur.inf.lab2.pz.servicemanmanagement.timetable.Timetable;
import ur.inf.lab2.pz.servicemanmanagement.timetable.dto.GroupData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.text.DateFormat;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Controller
public class ServicemanTimetableController {

    @FXML
    private Label dateFromLabel;
    @FXML private Label dateToLabel;
    @FXML private JFXButton undoButton;
    @FXML private JFXButton persistButton;
    @FXML private FlowPane datePanel;

    @FXML
    private AnchorPane placeForTimetable;

    @Autowired
    private ViewManager viewManager;

    @Autowired
    private TimetableManager timetableManager;
    private Timetable timetable;


    @FXML
    public void initialize() {
        initDatePanel();
        initTimetable();
    }

    private void initTimetable() {
        Parent editTaskDialog = viewManager.getComponent(ViewComponent.SERVICEMAN_EDIT_TASK_DIALOG);
        StackPane rootStackPane = viewManager.getRoot();
        timetable = timetableManager.createServicemanTimetable(rootStackPane, editTaskDialog);
        timetable.loadTasks(timetableManager.getAllAllocatedTasks(SecurityContext.getLoggedUser().getId()));
        viewManager.loadComponentInto(placeForTimetable, timetable.getView());
    }

    private void initDatePanel() {
        Date now = new Date();
        updateDateRangeLabels(DateUtils.getWeekDateRangeByDate(now));
    }

    private void updateDateRangeLabels(DateRange weekRange) {
        DateFormat format = DateUtils.DATE_FORMAT;

        String dateFromAsText = format.format(weekRange.getFrom());
        dateFromLabel.setText(dateFromAsText);

        String dateToAsText = format.format(weekRange.getTo());
        dateToLabel.setText(dateToAsText);
    }


    @FXML
    public void undoTasks() {
        initTimetable();
    }

    @FXML
    public void moveToNextWeek() {
        DateRange weekRange = timetable.nextWeek();
        updateDateRangeLabels(weekRange);
    }

    @FXML
    public void moveToPrevWeek() {
        DateRange weekRange = timetable.prevWeek();
        updateDateRangeLabels(weekRange);
    }

    private Logger log = Logger.getGlobal();


    @FXML
    private void persistTasks() {
        Set<AllocatedTask> allocatedTasks = timetable.dumpAllocatedTasks();
        log.info(String.valueOf(allocatedTasks.size()) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        timetableManager.saveAllocated(SecurityContext.getLoggedUser().getId(), allocatedTasks);
    }
}
