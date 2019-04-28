package ur.inf.lab2.pz.servicemanmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ur.inf.lab2.pz.servicemanmanagement.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
