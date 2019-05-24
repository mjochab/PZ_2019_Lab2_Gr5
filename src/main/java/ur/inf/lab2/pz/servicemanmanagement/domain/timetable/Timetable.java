package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import javafx.scene.control.Control;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;

import java.util.Collection;
import java.util.Set;

public interface Timetable {
    void loadTasks(Collection<AllocatedTask> tasksFromActualWeek);
    Control getView();
    Set<AllocatedTask> getAllocatedTasks();
    void clear();

    DateRange nextWeek();
    DateRange prevWeek();
}
