package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import jfxtras.scene.control.agenda.Agenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.*;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.Timetable;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.TimetableTask;
import ur.inf.lab2.pz.servicemanmanagement.domain.timetable.UnallocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;
import ur.inf.lab2.pz.servicemanmanagement.service.TimetableManager;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

class GroupData {
    private Long leaderId;
    private String groupName;

    public GroupData(Long leaderId, String groupName) {
        this.leaderId = leaderId;
        this.groupName = groupName;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return groupName;
    }
}


@Controller
public class TimetableController implements Initializable {

//    ObservableList<TimetableTask> tasks;
    @Autowired
    private ViewManager viewManager;
    @Autowired
    private TaskRepository taskRepository;
    @FXML
    private JFXButton NewTaskButton;
    @FXML
    private JFXTreeTableView<UnallocatedTask> tasksTableView;

    @FXML
    private AnchorPane placeForTimetable;

    @FXML private JFXComboBox<GroupData> serviceTeamSelect;

    @Autowired
    private TimetableManager timetableManager;
    private Timetable timetable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        loadTable();

        //TODO DEV
        serviceTeamSelect.getItems().add(new GroupData(1L, "Grupa śmieci"));
        serviceTeamSelect.getItems().add(new GroupData(1L, "Grupa upośledzonych"));
        serviceTeamSelect.getItems().add(new GroupData(1L, "Grupa kretynów"));
        //

        initTimetable();
        viewManager.loadComponentInto(placeForTimetable, ViewComponent.TIMETABLE_GROUP_NOT_SELECTED);
    }

    private void initTimetable() {
        timetable = timetableManager.createTimetable(tasksTableView);
    }

    public void groupChanged(ActionEvent event) {
        GroupData selectedGroup = serviceTeamSelect.getSelectionModel().getSelectedItem();

        timetable.loadTasks(timetableManager.getTasksFromActualWeek(selectedGroup.getLeaderId()));
        viewManager.loadComponentInto(placeForTimetable, timetable.getView());
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

    public void loadTable() {
//        tasks = FXCollections.observableArrayList(timetableManager.getUnallocatedTasks());
//        TreeItem<TimetableTask> root = new RecursiveTreeItem<>(tasks, RecursiveTreeObject::getChildren);

        tasksTableView.setRoot(timetableManager.getUnallocatedTasksAsTreeItem());
        tasksTableView.setShowRoot(false);
    }

    @FXML
    void openNewTaskDialog(ActionEvent event) throws IOException {
        viewManager.openDialog(ViewComponent.NEW_TASK_DIALOG);
    }

}
