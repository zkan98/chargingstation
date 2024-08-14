package elice.chargingstationbackend.charger.dto;

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
    private String id;           // 프론트엔드의 id에 해당 (충전소 ID)
    private String name;         // 프론트엔드의 name에 해당 (충전소명)
    private String address;      // 프론트엔드의 address에 해당 (주소)
    private String detailAddress;// 프론트엔드의 detailAddress에 해당 (상세 주소)
    private String price;        // 프론트엔드의 price에 해당 (가격, 이 필드는 문자열로 처리됩니다)
    private String slot;         // 프론트엔드의 slot에 해당 (슬롯 수)
    private String connector;    // 프론트엔드의 connector에 해당 (커넥터 유형)
    private String speed;        // 프론트엔드의 speed에 해당 (충전속도)
    private String fee;          // 프론트엔드의 fee에 해당 (충전요금)
    private String parkingFee;   // 프론트엔드의 parkingFee에 해당 (주차 요금)
    private Double lat;          // 위도
    private Double lng;          // 경도

    public Charger toEntity() {
        return Charger.builder()
            .statId(id)
            .statNm(name)
            .addr(address)
            .detailAddr(detailAddress)
            .chargingFee(price != null ? Double.parseDouble(price) : null)
            .slot(slot != null ? Integer.parseInt(slot) : null)
            .output(speed)
            .chgerType(connector)
            .parkingFree(parkingFee)
            .lat(lat)
            .lng(lng)
            .build();
    }
}
