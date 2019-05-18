package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;

import java.util.List;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {
    List<User> findAllByEmail(@Param("email") String email);

}