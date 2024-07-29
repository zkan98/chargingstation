package elice.chargingstationbackend.business.controller;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.business.service.BusinessOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class BusinessOwnerController {

    @Autowired
    private final BusinessOwnerService businessOwnerService;

    @PostMapping
    public ResponseEntity<BusinessOwner> registerBusinessOwner(@RequestBody BusinessOwner businessOwner) {
        BusinessOwner createdBusinessOwner = businessOwnerService.registerBusinessOwner(businessOwner);
        return ResponseEntity.ok(createdBusinessOwner);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<BusinessOwner> getBusinessOwner(@PathVariable Long ownerId) {
        BusinessOwner businessOwner = businessOwnerService.getBusinessOwner(ownerId);
        return ResponseEntity.ok(businessOwner);
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<BusinessOwner> updateBusinessOwner(@PathVariable Long ownerId, @RequestBody BusinessOwner businessOwnerDetails) {
        BusinessOwner updatedBusinessOwner = businessOwnerService.updateBusinessOwner(ownerId, businessOwnerDetails);
        return ResponseEntity.ok(updatedBusinessOwner);
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteBusinessOwner(@PathVariable Long ownerId) {
        businessOwnerService.deleteBusinessOwner(ownerId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/{ownerId}/chargers")
//    public ResponseEntity<List<ChargingStation>> getOwnerChargingStations(@PathVariable Long ownerId) {
//        BusinessOwner businessOwner = businessOwnerService.getBusinessOwner(ownerId);
//        if (businessOwner != null) {
//            return ResponseEntity.ok(businessOwner.getChargingStations().stream().toList());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
