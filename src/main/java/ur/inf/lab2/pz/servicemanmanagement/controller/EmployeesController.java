package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.ServicemanDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.workerAddDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.validator.WorkerAddValidator;
import ur.inf.lab2.pz.servicemanmanagement.repository.ServicemanRepository;
import ur.inf.lab2.pz.servicemanmanagement.services.EmployeeService;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewManager;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class EmployeesController implements Initializable {

    private ObservableList<ServicemanDTO> servicemansDTO;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ServicemanRepository servicemanRepository;

    @Autowired
    private ViewManager viewManager;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXTextField groupNameInput;

    @FXML
    private Text emailAlert;

    @FXML
    private Text existingUserAlert;

    @FXML
    private Text emptyGroupAlert;

    @FXML
    private JFXTreeTableView<ServicemanDTO> servicemansTableView;

    @FXML
    private StackPane stackPane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emailAlert.setVisible(false);
        existingUserAlert.setVisible(false);
        emptyGroupAlert.setVisible(false);
        initTableColumns();
        fetchServicemans();
        loadTable();
    }

    private void initTableColumns() {
        TreeTableColumn emailCol = new TreeTableColumn("Adres email");
        TreeTableColumn firstNameCol = new TreeTableColumn("Imię");
        TreeTableColumn lastNameCol = new TreeTableColumn("Nazwisko");

        TreeTableColumn isActiveCol = new TreeTableColumn("Aktywny");
        TreeTableColumn groupNameCol = new TreeTableColumn("Nazwa grupy");

        servicemansTableView.getColumns().addAll(firstNameCol,lastNameCol, emailCol, isActiveCol, groupNameCol);

        emailCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("email"));
        isActiveCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("enabled"));
        groupNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("groupName"));
        firstNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("firstName"));
        lastNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("lastName"));

    }

    public void loadTable() {
        TreeItem<ServicemanDTO> root = new RecursiveTreeItem<>(servicemansDTO, RecursiveTreeObject::getChildren);

        servicemansTableView.setRoot(root);
        servicemansTableView.setShowRoot(false);
    }

    private void fetchServicemans() {
        List<Serviceman> servicemans = servicemanRepository.findAllByManager_id(SecurityContext.getLoggedUser().getId());
        List<ServicemanDTO> servicemanDTOS = new ArrayList<>();

        for (Serviceman serviceman : servicemans) {
            ServicemanDTO servicemanDTO = new ServicemanDTO();
            servicemanDTO.setEmail(serviceman.getEmail());
            servicemanDTO.setGroupName(serviceman.getGroupName());
            servicemanDTO.setFirstName(serviceman.getFirstName());
            servicemanDTO.setLastName(serviceman.getLastName());

            if (serviceman.isEnabled()) servicemanDTO.setEnabled("TAK");
            else servicemanDTO.setEnabled("NIE");


            servicemanDTOS.add(servicemanDTO);
        }
        servicemansDTO = FXCollections.observableArrayList(servicemanDTOS);
    }

    public void addWorker() throws IOException {
        if (validate()) {
            workerAddDTO dto = new workerAddDTO(emailInput.getText(), groupNameInput.getText());
            employeeService.addWorker(dto, existingUserAlert);

            String emailAddress = emailInput.getText();
            viewManager.loadComponent(ViewComponent.EMPLOYEES);
            loadDialog(emailAddress);
        }
    }

    private boolean validate() {
        WorkerAddValidator validator = new WorkerAddValidator(
                emailInput.getText(),
                groupNameInput.getText(),
                emailAlert,
                emptyGroupAlert,
                existingUserAlert);
        validator.validate();
        return validator.getValidator().isClean();
    }


    @FXML
    private void loadDialog(String email) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Sukces"));
        content.setBody(new Text("Wysłano początkowe dane do logowania na adres: " + email));
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("OK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button);
        dialog.show();
    }


}

