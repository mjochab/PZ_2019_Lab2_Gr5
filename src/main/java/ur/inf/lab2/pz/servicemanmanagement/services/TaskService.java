package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.SecurityContext;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewTaskDTO;
import ur.inf.lab2.pz.servicemanmanagement.domain.enums.TaskStatus;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;

import java.util.Date;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void saveNewTask(NewTaskDTO newTaskDTO) {

        Task newTask = new Task();

        newTask.setTitle(newTaskDTO.getTitle());
        newTask.setDetails(newTaskDTO.getDetails());
        newTask.setClient(newTaskDTO.getClient());
        newTask.setAuthor(SecurityContext.getLoggedUser());
        newTask.setCreationDate(new Date());
        newTask.setStatus(TaskStatus.TODO);

        taskRepository.save(newTask);
    }

}
