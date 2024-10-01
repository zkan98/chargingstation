package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerRequestDTO {
    private String statId;
    private String statNm;
    private String addr;
    private Double chargingFee;
    private String output;  // 충전속도
    private String chgerType;  // 충전기 타입
    private String parkingFree;
    private Double lat;
    private Double lng;
    private Long ownerId;
    private String kind;  // 장소 유형 추가

    public Charger toEntity(BusinessOwner businessOwner) {
        return Charger.builder()
            .statId(statId)
            .statNm(statNm)
            .addr(addr)
            .chargingFee(chargingFee)
            .output(output)  // 충전속도 필드 추가
            .chgerType(chgerType)  // 충전기 타입 필드 추가
            .parkingFree(parkingFree)
            .lat(lat)
            .lng(lng)
            .businessOwner(businessOwner)
            .kind(kind)  // 장소 유형 필드 추가
            .build();
    }
}
