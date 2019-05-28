package ur.inf.lab2.pz.servicemanmanagement.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.TaskStatusDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.ServicemanRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class ManagerDashboardController {


    @FXML
    private StackPane servicemansStackPane;

    @Autowired
    private ServicemanRepository servicemanRepository;

    @Autowired
    private TaskRepository taskRepository;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");



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
            vBox.setPrefHeight(150);
            vBox.setPrefWidth(200);
            vBox.setAlignment(Pos.TOP_CENTER);

            TaskStatusDTO userTask = getCurrentStatus(serviceman);

            vBox.setOnMouseClicked(event -> {
                showTimetable(new Long(1));
                loadDialog(userTask.getTag(), userTask.getDesciption());
            });

            flowPane.getChildren().add(vBox);

            if(!serviceman.isEnabled()){
                vBox.getChildren().addAll(new Label("Użytkownik nie zarejestrowany"),
                        new Label(serviceman.getEmail()));

                String style = "-fx-background-color: #A9A9A9;" +
                        "-fx-font-size: 10px;";
                vBox.setStyle(style);
            }
            else if (userTask.getTag().equals("WOLNY")) {
                vBox.getChildren().addAll(new Label(serviceman.getFirstName() + " " + serviceman.getLastName()),
                        new Label(serviceman.getGroupName()),
                        new Label(),
                        new Label(userTask.getTag()));

                String style = "-fx-background-color: #90BE6D;" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;";
                vBox.setStyle(style);
            }

            else {

                String formatDateTimeTo = userTask.getDateTo().format(formatter);
                String formatDateTimeFrom = userTask.getDateFrom().format(formatter);

                vBox.getChildren().addAll(new Label(serviceman.getFirstName() + " " + serviceman.getLastName()),
                        new Label(serviceman.getGroupName()),
                        new Label(),
                        new Label(userTask.getTag()),
                        new Label(userTask.getDesciption()),
                        new Label(),
                        new Label(formatDateTimeFrom + " - " + formatDateTimeTo));

                String style = "-fx-background-color: #B3E5FC;" +
                        "-fx-font-size: 10px;" +
                        "-fx-font-weight: bold;";
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
                dto.setDesciption(task.getDescription());
                dto.setDateFrom(task.getDateTimeFrom());

                dto.setDateTo(task.getDateTimeTo());
            }
        }
        return dto;
    }

    @FXML
    private void loadDialog(String tag, String desc) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(tag));
        content.setBody(new Text(desc));
        JFXDialog dialog = new JFXDialog(servicemansStackPane, content, JFXDialog.DialogTransition.CENTER);
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