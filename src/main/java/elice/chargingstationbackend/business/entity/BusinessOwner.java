package elice.chargingstationbackend.business.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.Email;


@Getter
@Entity
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String ownerName;

    @NotBlank
    @Email
    @Size(max = 50)
    @Column(length = 50, nullable = false, unique = true)
    private String ownerEmail;

    @NotBlank
    @Size(max = 60)
    @Column(length = 60, nullable = false)
    private String ownerPassword;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String businessName;

    @NotBlank
    @Size(max = 20)
    @Column(length = 20, nullable = false)
    private String contactInfo;

//    @OneToMany(mappedBy = "businessOwner")
//    private Set<ChargingStation> chargingStations;

    public void updateDetails(BusinessOwner businessOwnerDetails) {
        this.ownerName = businessOwnerDetails.getOwnerName();
        this.ownerEmail = businessOwnerDetails.getOwnerEmail();
        this.ownerPassword = businessOwnerDetails.getOwnerPassword();
        this.businessName = businessOwnerDetails.getBusinessName();
        this.contactInfo = businessOwnerDetails.getContactInfo();
    }

}
