package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerListResponseDTO {
    private Long chargerId;
    private String businessName;
    private String chargerName;
    private String address;
    private String connectorType;
    private double chargingSpeed;
    private double chargingFee;
    private int parkingFee;
    private int slots;
    private int availableSlots;

    public ChargerListResponseDTO(Charger charger) {
        this.chargerId = charger.getId();
        // this.businessName = charger.getOwner().getBusinessName();
        this.chargerName = charger.getChargerName();
        this.address = charger.getAddress();
        this.connectorType = charger.getConnectorType();
        this.chargingSpeed = charger.getChargingSpeed();
        this.chargingFee = charger.getChargingFee();
        this.slots = charger.getSlots();
        this.availableSlots = charger.getAvailableSlots();
    }
}
