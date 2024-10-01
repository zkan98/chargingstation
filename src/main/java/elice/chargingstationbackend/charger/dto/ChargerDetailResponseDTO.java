package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDetailResponseDTO {
    private String statId;
    private String busiNm;
    private String busiCall;
    private String statNm;
    private String output;
    private String addr;
    private String chgerType;
    private Double chargingFee;
    private String stat;
    private Double lat;
    private Double lng;

    public ChargerDetailResponseDTO(Charger charger) {
        this.busiNm = (charger.getBusinessOwner() != null) ? charger.getBusinessOwner().getBusinessName() : null;
        this.busiCall = (charger.getBusinessOwner() != null) ? charger.getBusinessOwner().getBusinessCall() : null;
        this.statId = charger.getStatId();
        this.statNm = charger.getStatNm();
        this.output = charger.getOutput();
        this.addr = charger.getAddr();
        this.chgerType = charger.getChgerType();
        this.chargingFee = charger.getChargingFee();
        this.stat = charger.getStat();
        this.lat = charger.getLat();
        this.lng = charger.getLng();
    }

}
