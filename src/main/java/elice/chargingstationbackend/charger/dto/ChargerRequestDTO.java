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
    private String statId;
    private String statNm;
    private String addr;
    private String stat;
    private String speed;
    private Double chargingFee;
    private String chgerType;
    private String powerType;
    private String parkingFree;
    private Double lat;
    private Double lng;

    public Charger toEntity() {
        return Charger.builder()
            .statId(statId)
            .statNm(statNm)
            .addr(addr)
            .stat(stat)
            .speed(speed)
            .chargingFee(chargingFee)
            .chgerType(chgerType)
            .powerType(powerType)
            .parkingFree(parkingFree)
            .lat(lat)
            .lng(lng)
            .build();
    }
}
