package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.*;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;
import ur.inf.lab2.pz.servicemanmanagement.timetable.Timetable;
import ur.inf.lab2.pz.servicemanmanagement.timetable.dto.GroupData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.impl.UnallocatedTaskTableItem;
import ur.inf.lab2.pz.servicemanmanagement.services.TimetableManager;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.utils.DateUtils;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TimetableController implements Initializable {

//    ObservableList<AllocatedTask> tasks;
    @Autowired
    private ViewManager viewManager;
    @Autowired
    private TaskRepository taskRepository;
    @FXML
    private JFXButton NewTaskButton;
    @FXML
    private JFXTreeTableView<UnallocatedTaskTableItem> tasksTableView;

    @FXML
    private AnchorPane placeForTimetable;

    @FXML private JFXComboBox<GroupData> serviceTeamSelect;

    @Autowired
    private TimetableManager timetableManager;
    private Timetable timetable;

    @FXML private Label dateFromLabel;
    @FXML private Label dateToLabel;
    @FXML private JFXButton undoButton;
    @FXML private JFXButton persistButton;
    @FXML private FlowPane datePanel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initGroups();
        initUnallocatedTaskTable();
        initTimetable();
        turnOffTimetableSettingControls();
        initDatePanel();
        viewManager.loadComponentInto(placeForTimetable, ViewComponent.TIMETABLE_GROUP_NOT_SELECTED);
    }

    private void initGroups() {
        List<GroupData> groups = timetableManager.getAllGroups();
        serviceTeamSelect.getItems().addAll(groups);
    }

    private void initDatePanel() {
        Date now = new Date();
        updateDateRangeLabels(DateUtils.getWeekDateRangeByDate(now));
    }

    private void turnOffTimetableSettingControls() {
        undoButton.setVisible(false);
        persistButton.setVisible(false);
        datePanel.setVisible(false);
    }

    private void turnOnTimetableSettingControls() {
        undoButton.setVisible(true);
        persistButton.setVisible(true);
        datePanel.setVisible(true);
    }


    private void initUnallocatedTaskTable() {
        initTableColumns();
        fetchUnallocatedTasksToTable();
    }

    private void initTableColumns() {
        TreeTableColumn idCol = new TreeTableColumn("Identyfikator");
        TreeTableColumn tagCol = new TreeTableColumn("Tag");
        TreeTableColumn descriptionCol = new TreeTableColumn("Opis");

        tasksTableView.getColumns().addAll(idCol, tagCol, descriptionCol);

        idCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("id"));
        tagCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("tag"));
        descriptionCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("description"));

    }

    public void fetchUnallocatedTasksToTable() {
        tasksTableView.setRoot(timetableManager.getUnallocatedTasksAsTreeItem());
        tasksTableView.setShowRoot(false);
    }

    private void initTimetable() {
        Parent editTaskDialog = viewManager.getComponent(ViewComponent.EDIT_TASK_DIALOG);
        StackPane rootStackPane = viewManager.getRoot();
        timetable = timetableManager.createTimetable(tasksTableView, rootStackPane, editTaskDialog);
    }

    public void groupChanged(ActionEvent event) {
        GroupData selectedGroup = serviceTeamSelect.getSelectionModel().getSelectedItem();
        fetchTasks(selectedGroup.getLeaderId());
        turnOnTimetableSettingControls();
    }



    @FXML
    public void undoTasks() {
        GroupData selectedGroup = serviceTeamSelect.getSelectionModel().getSelectedItem();
        fetchTasks(selectedGroup.getLeaderId());
    }

    private void fetchTasks(Long leaderId) {
        fetchUnallocatedTasksToTable();
        initTimetable();
        timetable.loadTasks(timetableManager.getAllAllocatedTasks(leaderId));
        viewManager.loadComponentInto(placeForTimetable, timetable.getView());
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

    private void updateDateRangeLabels(DateRange weekRange) {
        DateFormat format = DateUtils.DATE_FORMAT;

        String dateFromAsText = format.format(weekRange.getFrom());
        dateFromLabel.setText(dateFromAsText);

        String dateToAsText = format.format(weekRange.getTo());
        dateToLabel.setText(dateToAsText);
    }

    @FXML
    private void persistTasks() {
        Set<AllocatedTask> allocatedTasks = timetable.dumpAllocatedTasks();
        Set<UnallocatedTask> unallocatedTasks = tasksTableView.getRoot().getChildren().stream()
                .map(TreeItem::getValue)
                .collect(Collectors.toSet());

        timetableManager.save(allocatedTasks, unallocatedTasks);
    }


    @FXML
    void openNewTaskDialog(ActionEvent event) {
        viewManager.openDialog(ViewComponent.NEW_TASK_DIALOG);
    }

}
