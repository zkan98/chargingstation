package elice.chargingstationbackend.business.service;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.repository.BusinessOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessOwnerService {


    private final BusinessOwnerRepository businessOwnerRepository;

    public BusinessOwner registerBusinessOwner(BusinessOwner businessOwner) {
        return businessOwnerRepository.save(businessOwner);
    }

    public BusinessOwner getBusinessOwner(Long ownerId) {
        return businessOwnerRepository.findById(ownerId).orElse(null);
    }

    public BusinessOwner updateBusinessOwner(Long ownerId, BusinessOwner businessOwnerDetails) {
        BusinessOwner businessOwner = businessOwnerRepository.findById(ownerId).orElse(null);
        if (businessOwner != null) {
            businessOwner.updateDetails(
                    businessOwnerDetails.getOwnerName(),
                    businessOwnerDetails.getOwnerEmail(),
                    businessOwnerDetails.getOwnerPassword(),
                    businessOwnerDetails.getBusinessName(),
                    businessOwnerDetails.getContactInfo()
            );
            return businessOwnerRepository.save(businessOwner);
        } else {
            return null;
        }
    }

    public void deleteBusinessOwner(Long ownerId) {
        businessOwnerRepository.deleteById(ownerId);
    }

    public List<BusinessOwner> getAllBusinessOwners() {
        return businessOwnerRepository.findAll();
    }
}
