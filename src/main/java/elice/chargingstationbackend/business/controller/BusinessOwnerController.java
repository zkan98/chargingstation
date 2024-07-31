package elice.chargingstationbackend.business.controller;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.service.BusinessOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/business-owners")
@RequiredArgsConstructor
public class BusinessOwnerController {

    private final BusinessOwnerService businessOwnerService;

    @PostMapping
    public ResponseEntity<BusinessOwner> registerBusinessOwner(@RequestBody BusinessOwner businessOwner) {
        BusinessOwner registeredOwner = businessOwnerService.registerBusinessOwner(businessOwner);
        return ResponseEntity.ok(registeredOwner);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<BusinessOwner> getBusinessOwner(@PathVariable Long ownerId) {
        BusinessOwner businessOwner = businessOwnerService.getBusinessOwner(ownerId);
        return ResponseEntity.ok(businessOwner);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<BusinessOwner> updateBusinessOwner(@PathVariable Long ownerId, @RequestBody BusinessOwner businessOwnerDetails) {
        BusinessOwner updatedOwner = businessOwnerService.updateBusinessOwner(ownerId, businessOwnerDetails);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteBusinessOwner(@PathVariable Long ownerId) {
        businessOwnerService.deleteBusinessOwner(ownerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BusinessOwner>> getAllBusinessOwners() {
        List<BusinessOwner> businessOwners = businessOwnerService.getAllBusinessOwners();
        return ResponseEntity.ok(businessOwners);
    }
}
