package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ur.inf.lab2.pz.servicemanmanagement.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllByEmail(@Param("email") String email);

//    @Query("SELECT u FROM User u WHERE LOWER (u.email) LIKE :email AND LOWER (u.password) LIKE :password")
//    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}