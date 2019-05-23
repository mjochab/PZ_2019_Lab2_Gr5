package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXTextField;
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
        TreeTableColumn isActiveCol = new TreeTableColumn("Stan konta");
        TreeTableColumn groupNameCol = new TreeTableColumn("Nazwa grupy");

        servicemansTableView.getColumns().addAll(emailCol, isActiveCol, groupNameCol);

        emailCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("email"));
        isActiveCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("enabled"));
        groupNameCol.setCellValueFactory(new TreeItemPropertyValueFactory<User, Boolean>("groupName"));
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
            servicemanDTO.setEnabled(serviceman.isEnabled());
            servicemanDTOS.add(servicemanDTO);
        }
        servicemansDTO = FXCollections.observableArrayList(servicemanDTOS);
    }

    public void addWorker() {
        if (validate()) {
            workerAddDTO dto = new workerAddDTO(emailInput.getText(), groupNameInput.getText());
            employeeService.addWorker(dto, existingUserAlert);
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


}

