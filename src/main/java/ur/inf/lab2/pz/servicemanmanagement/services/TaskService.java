package ur.inf.lab2.pz.servicemanmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.domain.dto.NewTaskDTO;
import ur.inf.lab2.pz.servicemanmanagement.repository.TaskRepository;

/**
 * Klasa wykorzystywana do tworzenia nowego zadania
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Metoda obsługująca tworzenie nowego zadania
     * @param newTaskDTO parametr określający nowe zadanie
     * @return zwraca repozytorium zadań zaktualizowane o nowo dodane zadanie
     */
    public Task saveNewTask(NewTaskDTO newTaskDTO) {
        Task newTask = new Task(
                newTaskDTO.getTitle(),
                newTaskDTO.getDetails(),
                newTaskDTO.getClient()
        );

        return taskRepository.save(newTask);
    }

}
