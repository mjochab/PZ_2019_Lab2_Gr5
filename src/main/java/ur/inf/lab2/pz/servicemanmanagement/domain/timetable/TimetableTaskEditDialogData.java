package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

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
}