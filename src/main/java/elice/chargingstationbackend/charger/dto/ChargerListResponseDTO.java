package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerListResponseDTO {
    private String businessName;
    private String chargerName;
    private String address;
    private String connectorType;
    private double chargingFee;
    private int slots;
    private int availableSlots;

    public ChargerListResponseDTO(Charger charger) {
        // this.businessName = charger.getOwner().getBusinessName();
        this.chargerName = charger.getChargerName();
        this.address = charger.getAddress();
        this.connectorType = charger.getConnectorType();
        this.chargingFee = charger.getChargingFee();
        this.slots = charger.getSlots();
        this.availableSlots = charger.getAvailableSlots();
    }
}
