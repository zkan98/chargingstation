package elice.chargingstationbackend.business.controller;

import elice.chargingstationbackend.business.dto.BusinessOwnerDTO;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.service.BusinessOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/business-owners")
@RequiredArgsConstructor
public class BusinessOwnerController {

    private final BusinessOwnerService businessOwnerService;

    @PostMapping
    public ResponseEntity<BusinessOwnerDTO> registerBusinessOwner(@RequestBody BusinessOwnerDTO businessOwnerDTO) {
        BusinessOwner registeredOwner = businessOwnerService.registerBusinessOwner(businessOwnerDTO.toEntity());
        return ResponseEntity.ok(BusinessOwnerDTO.fromEntity(registeredOwner));
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<BusinessOwnerDTO> getBusinessOwner(@PathVariable Long ownerId) {
        BusinessOwner businessOwner = businessOwnerService.getBusinessOwner(ownerId);
        return ResponseEntity.ok(BusinessOwnerDTO.fromEntity(businessOwner));
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<BusinessOwnerDTO> updateBusinessOwner(@PathVariable Long ownerId, @RequestBody BusinessOwnerDTO businessOwnerDTO) {
        BusinessOwner updatedOwner = businessOwnerService.updateBusinessOwner(ownerId, businessOwnerDTO.toEntity());
        return ResponseEntity.ok(BusinessOwnerDTO.fromEntity(updatedOwner));
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteBusinessOwner(@PathVariable Long ownerId) {
        businessOwnerService.deleteBusinessOwner(ownerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BusinessOwnerDTO>> getAllBusinessOwners() {
        List<BusinessOwner> businessOwners = businessOwnerService.getAllBusinessOwners();
        List<BusinessOwnerDTO> businessOwnerDTOs = businessOwners.stream()
            .map(BusinessOwnerDTO::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(businessOwnerDTOs);
    }
}
