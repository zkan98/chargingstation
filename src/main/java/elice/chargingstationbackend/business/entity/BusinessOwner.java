package elice.chargingstationbackend.business.entity;

import elice.chargingstationbackend.charger.entity.Charger;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ownerId;

    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String ownerName = "Default Name";  // Default value

    @Email
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String ownerEmail = "default@example.com";  // Default value

    @Size(max = 60)
    @Column(length = 60, nullable = false)
    private String ownerPassword = "defaultPassword";  // Default value

    @Column(name = "busi_id", nullable = false, unique = true)
    private String busiId = "defaultBusiId";  // Default value

    @Column(name = "busi_nm", nullable = false)
    private String busiNm = "defaultBusiNm";  // Default value

    @Column(name = "busi_call", nullable = false)
    private String busiCall = "defaultCall";  // Default value

    @Column(name = "bnm")
    private String bnm = "defaultBnm";  // Default value

    // OneToMany 관계 설정
    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Charger> chargers;

    @PrePersist
    public void prePersist() {
        if (this.ownerName == null) {
            this.ownerName = "Default Name";
        }
        if (this.ownerEmail == null) {
            this.ownerEmail = "default@example.com";
        }
        if (this.ownerPassword == null) {
            this.ownerPassword = "defaultPassword";
        }
        if (this.busiId == null) {
            this.busiId = "defaultBusiId";
        }
        if (this.busiNm == null) {
            this.busiNm = "defaultBusiNm";
        }
        if (this.busiCall == null) {
            this.busiCall = "defaultCall";
        }
        if (this.bnm == null) {
            this.bnm = "defaultBnm";
        }
    }

    public void updateDetails(BusinessOwner businessOwnerDetails) {
        this.ownerName = businessOwnerDetails.getOwnerName();
        this.ownerEmail = businessOwnerDetails.getOwnerEmail();
        this.ownerPassword = businessOwnerDetails.getOwnerPassword();
        this.busiId = businessOwnerDetails.getBusiId();
        this.busiNm = businessOwnerDetails.getBusiNm();
        this.busiCall = businessOwnerDetails.getBusiCall();
        this.bnm = businessOwnerDetails.getBnm();
    }
}
