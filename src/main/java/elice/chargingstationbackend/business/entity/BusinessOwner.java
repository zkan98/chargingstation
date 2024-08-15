package elice.chargingstationbackend.business.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class BusinessOwner extends User {

    @Column(name = "busi_id", unique = true)
    private String businessId;  // 사업자ID

    @Column(name = "busi_nm")
    private String businessName;  // 사업자 이름

    @Column(name = "busi_call")
    private String businessCall;  // 사업자 연락처

    @Column(name = "bnm")
    private String businessCorporateName;  // 법인명


    // OneToMany 관계 설정
    @OneToMany(mappedBy = "businessOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Charger> chargers;

    // 비즈니스 오너의 상세 정보 업데이트 메서드
    public void updateDetails(BusinessOwner businessOwnerDetails) {
        this.businessId = businessOwnerDetails.getBusinessId();
        this.businessName = businessOwnerDetails.getBusinessName();
        this.businessCall = businessOwnerDetails.getBusinessCall();
        this.businessCorporateName = businessOwnerDetails.getBusinessCorporateName();

        // User의 필드 업데이트
        super.setUsername(businessOwnerDetails.getUsername());
        super.setEmail(businessOwnerDetails.getEmail());
        super.setPassword(businessOwnerDetails.getPassword());
    }
}
