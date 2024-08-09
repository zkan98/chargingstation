package elice.chargingstationbackend.business.repository;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, Long> {
    Optional<BusinessOwner> findByBusiId(String busiId);
}
