package elice.chargingstationbackend.business.repository;


import elice.chargingstationbackend.business.entity.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, Long> {
}
