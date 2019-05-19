package ur.inf.lab2.pz.servicemanmanagement.domain.timetable;

import javafx.scene.control.Control;
import jfxtras.scene.control.agenda.Agenda;
import ur.inf.lab2.pz.servicemanmanagement.domain.DateRange;
import ur.inf.lab2.pz.servicemanmanagement.view.ViewComponent;

import java.util.Set;

public interface Timetable {
    void loadTasks(Set<TimetableTask> tasksFromActualWeek);
    DateRange getDateRange();
    Agenda generate();

    Control getView();
}
