package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.Client;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewTaskDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.NewTaskValidator;
import ur.inf.lab2.pz.servicemanmanagement.repository.ClientRepository;
import ur.inf.lab2.pz.servicemanmanagement.services.TaskService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class NewTaskDialogController implements Initializable {

    @Autowired
    TimetableController timetableController;
    ObservableList<Client> clients;
    @Autowired
    private ViewManager viewManager;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TaskService taskService;
    @FXML
    private JFXButton newClientButton, saveButton, cancelButton;
    @FXML
    private JFXTextField titleTextField;
    @FXML
    private JFXTextArea detailsTextArea;
    @FXML
    private JFXTreeTableView<Client> tableView;
    @FXML
    private Text titleAlert, detailsAlert, notSelectedAlert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableColumns();
        loadTable();
        titleAlert.setVisible(false);
        detailsAlert.setVisible(false);
    }

    @FXML
    void closeDialog(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openNewClientDialog(ActionEvent event) throws IOException {
        viewManager.openDialog(ViewComponent.NEW_CLIENT_DIALOG);
    }

    @FXML
    void saveTask(ActionEvent event) {
        if(validate()) {
            TreeItem<Client> client = tableView.getSelectionModel().getSelectedItem();
            if (client != null) {
                NewTaskDTO newTaskDTO = new NewTaskDTO(titleTextField.getText(), detailsTextArea.getText(), client.getValue());
                taskService.saveNewTask(newTaskDTO);
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                timetableController.loadTable();
            } else{
                notSelectedAlert.setText("Wybierz klienta z listy, lub dodaj jeśli jeszcze nie został zarejestrowany");
            }
        }
    }

    public void loadTable() {
        clients = FXCollections.observableArrayList(clientRepository.findAll());
        TreeItem<Client> root = new RecursiveTreeItem<>(clients, RecursiveTreeObject::getChildren);
        tableView.setRoot(root);
        tableView.setShowRoot(false);
    }

    private void initTableColumns() {
        TreeTableColumn firstNameCol = new TreeTableColumn("Imie");
        TreeTableColumn lastNameCol = new TreeTableColumn("Nazwisko");
        TreeTableColumn streetCol = new TreeTableColumn("Ulica");
        TreeTableColumn houseNumberCol = new TreeTableColumn("Numer domu");
        //  TreeTableColumn apartmentNumberCol = new TreeTableColumn("Numer mieszkania");
        TreeTableColumn cityCol = new TreeTableColumn("Miasto");

        tableView.getColumns().addAll(firstNameCol, lastNameCol, streetCol, houseNumberCol, /*apartmentNumberCol,*/ cityCol);

        firstNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("firstName"));
        lastNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("lastName"));
        streetCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("street"));
        houseNumberCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, Integer>("houseNumber"));
        // apartmentNumberCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, Integer>("apartmentNumber"));
        cityCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("city"));
    }

    private boolean validate() {

        NewTaskValidator validator = new NewTaskValidator(
                titleTextField.getText(),
                detailsTextArea.getText(),
                titleAlert,
                detailsAlert);

        validator.validate();
        return validator.getValidator().isClean();
    }
}
