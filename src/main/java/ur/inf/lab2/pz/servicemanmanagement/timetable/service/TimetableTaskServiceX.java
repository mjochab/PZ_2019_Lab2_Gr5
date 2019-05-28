package ur.inf.lab2.pz.servicemanmanagement.timetable.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;
import ur.inf.lab2.pz.servicemanmanagement.repository.AllUsersRepository;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;
import ur.inf.lab2.pz.servicemanmanagement.timetable.TimetableDatasource;
import ur.inf.lab2.pz.servicemanmanagement.timetable.dto.GroupData;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;

import java.util.*;

public class TimetableTaskServiceX implements TimetableDatasource {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AllUsersRepository userRepository;

    @Override
    public Set<AllocatedTask> getAllocatedTasks(Long leaderId) {

//        Set<AllocatedTask> allocatedTasks = taskRepository.findAllByAllocatedTrueAndTeamLeaderId(leaderId);
//
//        return allocatedTasks;
        return null;
    }

    @Override
    public Set<UnallocatedTask> getUnallocatedTasks() {

//        Set<UnallocatedTask> unallocatedTasks = taskRepository.findAllByAllocatedFalse();
//
//        return unallocatedTasks;
        return null;

    }

    @Override
    public void saveAllocated(Long leaderId, Collection<AllocatedTask> allocatedTasks) {

    }

    public void saveAllocated(Collection<AllocatedTask> allocatedTasks) {
//        Set<Task> tasksToSave = new HashSet<>();
//
//        allocatedTasks.forEach((allocatedTask) -> {
//
//            Task task = taskRepository.findById(allocatedTask.getId()).get();
//            task.setWholeDayTask(allocatedTask.isWholeDayTask());
//            task.setState(allocatedTask.getState());
//            task.setDateTimeFrom(allocatedTask.getDateTimeFrom());
//            task.setDateTimeTo(allocatedTask.getDateTimeTo());
//            task.setAllocated(true);
//            tasksToSave.add(task);
//
//        });
//
//        taskRepository.saveAll(tasksToSave);
    }

    @Override
    public void saveUnallocated(Collection<UnallocatedTask> unallocatedTasks) {

//        Set<Task> tasksToSave = new HashSet<>();
//        unallocatedTasks.forEach((unallocatedTask -> {
//            Task task = taskRepository.findById(unallocatedTask.getId()).get();
//            task.setWholeDayTask(false);
//            task.setDateTimeTo(null);
//            task.setDateTimeFrom(null);
//            task.setState(null);
//            task.setAllocated(false);
//            tasksToSave.add(task);
//        }));
//
//        taskRepository.saveAll(tasksToSave);
    }

    @Override
    public List<GroupData> getGroups() {

        return null;
//        List<User> activeServicemans = userRepository.findAllActiveServicemans();
//        List<GroupData> groups = new ArrayList<>();
//
//        for (User u : activeServicemans)
//        {
//            groups.add(new GroupData(u.getId(), u.getGroupName()));
//        }
//
//        return groups;
    }
}
