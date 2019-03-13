package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ur.inf.lab2.pz.servicemanmanagement.domain.MyEntity;

@Repository
public interface ExampleRepository extends CrudRepository<MyEntity, Long> {
}
