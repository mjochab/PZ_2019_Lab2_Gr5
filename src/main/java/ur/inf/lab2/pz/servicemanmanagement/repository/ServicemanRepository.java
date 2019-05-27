package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicemanRepository extends UserRepository<Serviceman> {

    List<Serviceman> findAll();

    @Query("SELECT s FROM Serviceman s WHERE s.enabled = true")
    List<Serviceman> findAllActivated();

    List<Serviceman> findAllByManager_id(Long id);


}
