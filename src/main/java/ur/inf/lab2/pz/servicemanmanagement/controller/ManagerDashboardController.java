package ur.inf.lab2.pz.servicemanmanagement.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.TaskStatusDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.ServicemanRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ManagerDashboardController {


    @FXML
    private StackPane servicemansStackPane;

    @Autowired
    private ServicemanRepository servicemanRepository;

    @Autowired
    private TaskRepository taskRepository;


    @FXML
    public void initialize() {
        System.out.println("inicjalizacja dashboard component");
        initServicemans();

    }


    private void initServicemans() {
        FlowPane flowPane = new FlowPane();
        flowPane.setVgap(15);
        flowPane.setHgap(15);

        List<Serviceman> servicemanList = servicemanRepository.findAll();

        for (Serviceman serviceman : servicemanList) {

            VBox vBox = new VBox();
            vBox.setPadding(new Insets(10, 20, 50, 20));
            vBox.setPrefHeight(250);
            vBox.setPrefWidth(300);

            TaskStatusDTO userTask = getCurrentStatus(serviceman);

            vBox.setOnMouseClicked(event -> {
                showTimetable(new Long(1));
            });

            flowPane.getChildren().add(vBox);


            if(!serviceman.isEnabled()){
                vBox.getChildren().addAll(new Label("Użytkownik nie zarejestrowany"),
                        new Label(serviceman.getEmail()));

                String style = "-fx-background-color: #A9A9A9;" +
                        "-fx-font-size: 15px";
                vBox.setStyle(style);
            }
            else if (userTask.getTag().equals("WOLNY")) {
                vBox.getChildren().addAll(new Label(serviceman.getFirstName() + " " + serviceman.getLastName()),
                        new Label(serviceman.getGroupName()),
                        new Label(userTask.getTag()));

                String style = "-fx-background-color: #90BE6D;" +
                        "-fx-font-size: 20px";
                vBox.setStyle(style);
            }

            else {
                vBox.getChildren().addAll(new Label(serviceman.getFirstName() + " " + serviceman.getLastName()),
                        new Label(serviceman.getGroupName()),
                        new Label(userTask.getTag()),
                        new Label(userTask.getDateFrom() + " - " + userTask.getDateTo()));

                String style = "-fx-background-color: #B3E5FC;" +
                        "-fx-font-size: 15px";
                vBox.setStyle(style);
            }
        }
        servicemansStackPane.getChildren().addAll(flowPane);
    }

    private void showTimetable(Long menagoId) {
        System.out.println(menagoId + "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
    }


    private TaskStatusDTO getCurrentStatus(Serviceman serviceman) {
        List<Task> tasks = taskRepository.findAllocated(serviceman.getId());
        LocalDateTime actualDate = LocalDateTime.now();

        TaskStatusDTO dto = new TaskStatusDTO();
        dto.setTag("WOLNY");

        for (Task task : tasks) {
            if (task.getDateTimeFrom().isBefore(actualDate) && task.getDateTimeTo().isAfter(actualDate)) {
                dto.setTag(task.getTag());
                dto.setDateFrom(task.getDateTimeFrom());
                dto.setDateTo(task.getDateTimeTo());
            }
        }
        return dto;
    }


}