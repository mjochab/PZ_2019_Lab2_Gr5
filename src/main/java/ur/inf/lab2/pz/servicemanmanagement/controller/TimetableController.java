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

    ObservableList<Task> tasks;
    @Autowired
    private ViewManager viewManager;
    @Autowired
    private TaskRepository taskRepository;
    @FXML
    private JFXButton NewTaskButton;
    @FXML
    private JFXTreeTableView<Task> tasksTableView;

    @FXML
    private AnchorPane placeForTimetable;

    @FXML private JFXComboBox<GroupData> serviceTeamSelect;

    @Autowired
    private TimetableManager timetableManager;

    public void groupChanged(ActionEvent event) {
        GroupData selectedGroup = serviceTeamSelect.getSelectionModel().getSelectedItem();
        Agenda agenda = timetableManager.load(selectedGroup.getLeaderId());
        viewManager.loadComponentInto(placeForTimetable, agenda);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        loadTable();

        //TODO DEV
        serviceTeamSelect.getItems().add(new GroupData(1L, "Grupa śmieci"));
        serviceTeamSelect.getItems().add(new GroupData(1L, "Grupa upośledzonych"));
        serviceTeamSelect.getItems().add(new GroupData(1L, "Grupa kretynów"));
        //

        viewManager.loadComponentInto(placeForTimetable, ViewComponent.TIMETABLE_GROUP_NOT_SELECTED);
    }


    private void initTableColumns() {
        TreeTableColumn titleCol = new TreeTableColumn("Tytuł");
        TreeTableColumn detailsCol = new TreeTableColumn("Opis");

        tasksTableView.getColumns().addAll(titleCol, detailsCol);

        titleCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("title"));
        detailsCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("details"));

    }

    public void loadTable() {
        tasks = FXCollections.observableArrayList(taskRepository.findAll());
        TreeItem<Task> root = new RecursiveTreeItem<>(tasks, RecursiveTreeObject::getChildren);
        tasksTableView.setRoot(root);
        tasksTableView.setShowRoot(false);
    }

    @FXML
    void openNewTaskDialog(ActionEvent event) throws IOException {
        viewManager.openDialog(ViewComponent.NEW_TASK_DIALOG);
    }

}
