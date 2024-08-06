package elice.chargingstationbackend.business.repository;

import elice.chargingstationbackend.business.entity.ApprovalRequest;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {
    List<ApprovalRequest> findByBusinessOwner(BusinessOwner businessOwner);
}
