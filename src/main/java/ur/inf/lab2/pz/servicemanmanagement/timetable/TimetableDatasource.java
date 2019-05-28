package ur.inf.lab2.pz.servicemanmanagement.timetable;

import ur.inf.lab2.pz.servicemanmanagement.timetable.dto.GroupData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TimetableDatasource {

    Set<AllocatedTask> getAllocatedTasks(Long leaderId);

    Set<UnallocatedTask> getUnallocatedTasks();

    void saveAllocated(Long leaderId, Collection<AllocatedTask> allocatedTasks);

    void saveUnallocated(Collection<UnallocatedTask> unallocatedTasks);

    List<GroupData> getGroups();
}
