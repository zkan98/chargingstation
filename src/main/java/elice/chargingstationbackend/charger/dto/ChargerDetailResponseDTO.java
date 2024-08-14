package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerDetailResponseDTO {
    private String id;         // statId로 변경
    private String businessName;  // busiNm으로 변경
    private String businessCall;  // busiCall로 변경
    private String name;       // statNm으로 변경
    private String speed;      // output으로 변경
    private String address;    // addr으로 변경
    private String connector;  // chgerType으로 변경
    private Double fee;        // chargingFee로 변경
    private String status;     // stat으로 변경

    public ChargerDetailResponseDTO(Charger charger) {
        this.id = charger.getStatId();
        this.businessName = charger.getBusinessOwner().getBusinessName();
        this.businessCall = charger.getBusinessOwner().getBusinessCall();
        this.name = charger.getStatNm();
        this.speed = charger.getOutput();
        this.address = charger.getAddr();
        this.connector = charger.getChgerType();
        this.fee = charger.getChargingFee();
        this.status = charger.getStat();
    }
}
