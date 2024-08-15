package elice.chargingstationbackend.business.service;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.exception.BusinessOwnerNotFoundException;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import elice.chargingstationbackend.user.Role;
import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;
    private final UserRepository userRepository;  // User repository 필요

    public BusinessOwner registerBusinessOwner(BusinessOwner businessOwner) {
        // 역할 추가
        businessOwner.getRoles().add(Role.ROLE_BUSINESS);
        // BusinessOwner 엔티티를 직접 저장
        return businessOwnerRepository.save(businessOwner);
    }


    public BusinessOwner getBusinessOwner(Long ownerId) {
        return businessOwnerRepository.findById(ownerId)
            .orElseThrow(() -> new BusinessOwnerNotFoundException(ownerId));
    }

    public BusinessOwner updateBusinessOwner(Long ownerId, BusinessOwner businessOwnerDetails) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(ownerId)
            .orElseThrow(() -> new BusinessOwnerNotFoundException(ownerId));
        businessOwner.updateDetails(businessOwnerDetails);
        return businessOwnerRepository.save(businessOwner);
    }

    public void deleteBusinessOwner(Long ownerId) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(ownerId)
            .orElseThrow(() -> new BusinessOwnerNotFoundException(ownerId));
        businessOwnerRepository.delete(businessOwner);
    }

    public List<BusinessOwner> getAllBusinessOwners() {
        return businessOwnerRepository.findAll();
    }
}
