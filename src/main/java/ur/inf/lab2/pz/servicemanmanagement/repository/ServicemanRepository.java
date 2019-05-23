package ur.inf.lab2.pz.servicemanmanagement.repository;

import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicemanRepository extends UserRepository<Serviceman> {

    List<Serviceman> findAll();

    List<Serviceman> findAllByManager_id(Long id);


}
