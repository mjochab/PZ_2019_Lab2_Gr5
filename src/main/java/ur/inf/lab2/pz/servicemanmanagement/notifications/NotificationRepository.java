package ur.inf.lab2.pz.servicemanmanagement.notifications;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import ur.inf.lab2.pz.servicemanmanagement.domain.Notification;

import java.util.List;

public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    //List<Notification> findAllByUserIdOrderByCreationDateDesc(long id, Pageable pageable);

    List<Notification> findAllByUserIdOrderByCreationDateDesc(long id);
    void deleteByIdIn(List<Long> ids);
}
