package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.Client;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        loadTable();
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
