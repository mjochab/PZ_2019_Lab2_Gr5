package ur.inf.lab2.pz.servicemanmanagement.repository;

import ur.inf.lab2.pz.servicemanmanagement.domain.User;

import javax.transaction.Transactional;

@Transactional
public interface AllUsersRepository extends UserRepository<User> {
}