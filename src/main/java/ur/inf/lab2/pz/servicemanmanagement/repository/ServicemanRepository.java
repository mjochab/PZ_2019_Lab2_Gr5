package ur.inf.lab2.pz.servicemanmanagement.repository;

import ur.inf.lab2.pz.servicemanmanagement.domain.Serviceman;

import javax.transaction.Transactional;

@Transactional
public interface ServicemanRepository extends UserRepository<Serviceman> {

}
