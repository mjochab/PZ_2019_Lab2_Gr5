package ur.inf.lab2.pz.servicemanmanagement.repository;

import ur.inf.lab2.pz.servicemanmanagement.domain.Manager;

import javax.transaction.Transactional;

@Transactional
public interface ManagerRepository extends UserRepository<Manager> {

}