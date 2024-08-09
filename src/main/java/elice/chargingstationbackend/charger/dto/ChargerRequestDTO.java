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
    private Double chargingFee;
    private String output;
    private String chgerType;
    private String parkingFree;
    private Double lat;
    private Double lng;

    public Charger toEntity() {
        return Charger.builder()
            .statId(statId)
            .statNm(statNm)
            .addr(addr)
            .chargingFee(chargingFee)
            .output(output)
            .chgerType(chgerType)
            .parkingFree(parkingFree)
            .lat(lat)
            .lng(lng)
            .build();
    }
}
