package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;

import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

//    @Query("SELECT task FROM Task task WHERE task.allocated AND task.teamLeader LIKE :id")
//    Set<AllocatedTask> findAllAllocatedTasksByLeaderId(@Param("id") Long id);

    Set<AllocatedTask> findAllByAllocatedTrueAndTeamLeaderId(@Param("id") Long id);

    Set<UnallocatedTask> findAllByAllocatedFalse();

}
