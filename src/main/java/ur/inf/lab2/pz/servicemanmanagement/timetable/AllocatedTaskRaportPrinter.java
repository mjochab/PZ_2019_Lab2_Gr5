package ur.inf.lab2.pz.servicemanmanagement.timetable;

import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;

public interface AllocatedTaskRaportPrinter {
    void print(AllocatedTask taskToPrint, String absolutePath);
}
