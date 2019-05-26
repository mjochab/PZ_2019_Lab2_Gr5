package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewestTaskDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;

import java.net.URL;
import java.util.*;

@Controller
public class ManagerDashboardController implements Initializable {

    ObservableList<NewestTaskDTO> tasksDTO;

    @FXML
    private JFXTreeTableView<NewestTaskDTO> newestTasksTable;

    @Autowired
    private TaskRepository taskRepository;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        fetchTasks();
        loadTable();
    }

    private void initTableColumns() {
        TreeTableColumn title = new TreeTableColumn("Tytuł");
        TreeTableColumn details = new TreeTableColumn("Opis");
        TreeTableColumn teamLeader = new TreeTableColumn("Lider zespołu");
        TreeTableColumn clientName = new TreeTableColumn("Klient");
        TreeTableColumn address = new TreeTableColumn("Adres");
        TreeTableColumn date = new TreeTableColumn("Data zgłoszenia");

        newestTasksTable.getColumns().addAll(title, details, teamLeader, clientName, address, date);

        title.setCellValueFactory(new TreeItemPropertyValueFactory<NewestTaskDTO, String>("title"));
        details.setCellValueFactory(new TreeItemPropertyValueFactory<NewestTaskDTO, String>("details"));
        teamLeader.setCellValueFactory(new TreeItemPropertyValueFactory<NewestTaskDTO, String>("teamLeader"));
        clientName.setCellValueFactory(new TreeItemPropertyValueFactory<NewestTaskDTO, String>("clientName"));
        address.setCellValueFactory(new TreeItemPropertyValueFactory<NewestTaskDTO, String>("address"));
        date.setCellValueFactory(new TreeItemPropertyValueFactory<NewestTaskDTO, Date>("creationDate"));
    }

    private void loadTable() {
        TreeItem<NewestTaskDTO> root = new RecursiveTreeItem<>(tasksDTO, RecursiveTreeObject::getChildren);
        newestTasksTable.setRoot(root);
        newestTasksTable.setShowRoot(false);
    }

    private void fetchTasks() {
        Page<Task> tasks = taskRepository.findNewest(new PageRequest(0, 5));
        List<NewestTaskDTO> newestTaskDTOS = new ArrayList<>();

        for (Task task : tasks) {
            NewestTaskDTO newestTaskDTO = new NewestTaskDTO();
            newestTaskDTO.setAddress(task.getClient().getStreet() + " " + task.getClient().getHouseNumber() +
                    "/" + task.getClient().getApartmentNumber());
            newestTaskDTO.setClientName(task.getClient().getFirstName() + " " + task.getClient().getLastName());
            newestTaskDTO.setCreationDate(task.getCreationDate());
            newestTaskDTO.setDetails(task.getDetails());
            newestTaskDTO.setTitle(task.getTitle());

            if (!task.hasTeamLeader()) newestTaskDTO.setTeamLeader("-");
            else newestTaskDTO.setTeamLeader(task.getTeamLeader().getFirstName() + task.getTeamLeader().getLastName());

            newestTaskDTOS.add(newestTaskDTO);
        }

        tasksDTO = FXCollections.observableArrayList(newestTaskDTOS);
    }
}


