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
    private String addr;
    private String chgerType;
    private Double chargingFee;
    private String stat;

    public ChargerDetailResponseDTO(Charger charger) {
        this.busiNm = charger.getBusinessOwner().getBusiNm();
        this.busiCall = charger.getBusinessOwner().getBusiCall();
        this.statId = charger.getStatId();
        this.statNm = charger.getStatNm();
        this.addr = charger.getAddr();
        this.chgerType = charger.getChgerType();
        this.chargingFee = charger.getChargingFee();
        this.stat = charger.getStat();
    }
}
