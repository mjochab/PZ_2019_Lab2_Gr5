package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ur.inf.lab2.pz.servicemanmanagement.domain.Task;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.AllocatedTask;
import ur.inf.lab2.pz.servicemanmanagement.timetable.task.UnallocatedTask;

import java.util.List;
import java.util.Set;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAll();


    @Query("SELECT t FROM Task t ORDER BY t.dateTimeFrom DESC")
    Page<Task> findNewest(Pageable pageable);


    @Query("SELECT t FROM Task t WHERE t.leader IS NULL")
    List<Task> findUnallocated();

    @Query("SELECT t FROM Task t WHERE t.leader.id = :leaderId")
    List<Task> findAllocated(@Param("leaderId") Long leaderId);

//    @Query("SELECT task FROM Task task WHERE task.allocated AND task.teamLeader LIKE :id")
//    Set<AllocatedTask> findAllAllocatedTasksByLeaderId(@Param("id") Long id);
//
//    Set<AllocatedTask> findAllByAllocatedTrueAndTeamLeaderId(@Param("id") Long id);
//
//    Set<UnallocatedTask> findAllByAllocatedFalse();

}
