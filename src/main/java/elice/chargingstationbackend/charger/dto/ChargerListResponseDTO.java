package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerListResponseDTO {
    private String statId;
    private String busiNm;
    private String statNm;
    private String addr;
    private String chgerType;
    private String output;
    private Double chargingFee;
    private String parkingFree;
    private String stat;
    private Double lat;
    private Double lng;

    public ChargerListResponseDTO(Charger charger) {
        this.statId = charger.getStatId();
        this.busiNm = charger.getBusinessOwner().getBusinessName();
        this.statNm = charger.getStatNm();
        this.addr = charger.getAddr();
        this.chgerType = charger.getChgerType();
        this.output = charger.getOutput();
        this.chargingFee = charger.getChargingFee();
        this.parkingFree = charger.getParkingFree();
        this.stat = charger.getStat();
        this.lat = charger.getLat();
        this.lng = charger.getLng();
    }

}
