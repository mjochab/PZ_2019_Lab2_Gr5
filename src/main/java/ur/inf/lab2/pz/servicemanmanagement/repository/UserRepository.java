package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;

import java.util.List;
import java.util.Set;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {
    List<User> findAllByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.enabled = true AND u.role.id=2")
    List<User> findAllActiveServicemans();

}