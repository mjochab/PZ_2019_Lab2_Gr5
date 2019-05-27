package ur.inf.lab2.pz.servicemanmanagement.timetable;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.ClientData;

import java.time.LocalDateTime;

public interface TimetableTaskEditDialogData {

    StackPane getStackPane();
    Node getView();

    Node getExitNode();

    void setTaskId(String id);

    void setTaskTag(String tag);

    void setTaskDescription(String description);

    void setDateTimeFrom(LocalDateTime dateTimeFrom);

    void setDateTimeTo(LocalDateTime dateTimeTo);

    void setWholeDay(boolean wholeDayTask);

    Node getDetachNode();

    Node getSaveNode();

    String getTaskDescription();

    LocalDateTime getDateTimeFrom();

    boolean isWholeDayTask();

    LocalDateTime getDateTimeTo();

    Node getPrintNode();

    void setClientData(ClientData clientData);

    void setState(String name);

    void disableDetachNode();

    void disableDescriptionNode();

    void disableDateFromNode();

    void disableTimeFromNode();

    void disableDateToNode();

    void disableTimeToNode();

    void disableWholeDayNode();

    void disableSaveNode();

    void clean();
}