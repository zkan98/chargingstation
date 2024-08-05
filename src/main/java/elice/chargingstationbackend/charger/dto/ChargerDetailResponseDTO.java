package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDetailResponseDTO {
    private Long chargerId;
    private String businessName;
    private String contactInfo;
    private String chargerName;
    private String address;
    private String connectorType;
    private Double chargingFee;
    private int slots;
    private int availableSlots;

    public ChargerDetailResponseDTO(Charger charger) {
        // this.businessName = charger.getBusinessOwner().getBusinessName();
        // this.contactInfo = charger.getBusinessOwner().getContactInfo();
        this.chargerId = charger.getId();
        this.chargerName = charger.getChargerName();
        this.address = charger.getAddress();
        this.connectorType = charger.getConnectorType();
        this.chargingFee = charger.getChargingFee();
        this.slots = charger.getSlots();
        this.availableSlots = charger.getAvailableSlots();
    }
}
