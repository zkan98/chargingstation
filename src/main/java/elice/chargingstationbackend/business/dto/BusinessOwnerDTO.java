package elice.chargingstationbackend.business.dto;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessOwnerDTO {

    private String busiId;  // 오픈API에서 사용되는 사업자ID
    private String busiNm;  // 오픈API에서 사용되는 사업자 이름
    private String busiCall;  // 오픈API에서 사용되는 사업자 연락처
    private String bnm;  // 오픈API에서 사용되는 법인명

    // User 관련 필드 추가
    private String email;
    private String username;
    private String password;

    // DTO에서 엔티티로 변환하는 메서드
    public BusinessOwner toEntity() {
        BusinessOwner owner = new BusinessOwner();
        owner.setBusinessId(this.busiId);
        owner.setBusinessName(this.busiNm);
        owner.setBusinessCall(this.busiCall);
        owner.setBusinessCorporateName(this.bnm);

        owner.setEmail(this.email);
        owner.setUsername(this.username);
        owner.setPassword(this.password);  // 패스워드 암호화 필요

        return owner;
    }

    // 엔티티에서 DTO로 변환하는 메서드
    public static BusinessOwnerDTO fromEntity(BusinessOwner businessOwner) {
        BusinessOwnerDTO dto = new BusinessOwnerDTO();
        dto.setBusiId(businessOwner.getBusinessId());
        dto.setBusiNm(businessOwner.getBusinessName());
        dto.setBusiCall(businessOwner.getBusinessCall());
        dto.setBnm(businessOwner.getBusinessCorporateName());

        dto.setEmail(businessOwner.getEmail());
        dto.setUsername(businessOwner.getUsername());
        dto.setPassword(businessOwner.getPassword());

        return dto;
    }
}
