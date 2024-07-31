package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargerRequestDTO {
    private String chargerName;
    private String address;
    private int slots;
    private double chargingSpeed;
    private double chargingFee;
    private String connectorType;
    private int parkingFee;
    private Double latitude;
    private Double longitude;

    public Charger toEntity() {
        Charger charger = Charger.builder()
                                .chargerName(chargerName)
                                .address(address)
                                .slots(slots)
                                .chargingSpeed(chargingSpeed)
                                .chargingFee(chargingFee)
                                .connectorType(connectorType)
                                .parkingFee(parkingFee)
                                .latitude(latitude)
                                .longitude(longitude)
                                .build();
        return charger;
    }
}
