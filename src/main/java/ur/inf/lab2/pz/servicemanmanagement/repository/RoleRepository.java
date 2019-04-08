package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ur.inf.lab2.pz.servicemanmanagement.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRole(@Param("role") String role);
}