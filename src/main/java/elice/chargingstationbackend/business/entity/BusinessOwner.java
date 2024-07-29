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
    @Size(max = 255)
    @Column(length = 255, nullable = false)
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

    public void updateDetails(String ownerName, String ownerEmail, String ownerPassword, String businessName, String contactInfo) {
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
        this.ownerPassword = ownerPassword;
        this.businessName = businessName;
        this.contactInfo = contactInfo;
    }


}
