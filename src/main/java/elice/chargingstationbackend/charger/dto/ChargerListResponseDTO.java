package elice.chargingstationbackend.charger.dto;

import elice.chargingstationbackend.charger.entity.Charger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChargerListResponseDTO {
    private String id;           // 프론트엔드의 id에 해당
    private String name;         // 프론트엔드의 name에 해당
    private String address;      // 프론트엔드의 address에 해당
    private String connector;    // 프론트엔드의 connector에 해당
    private String slot;         // 프론트엔드의 slot에 해당
    private String speed;        // 프론트엔드의 speed에 해당
    private String fee;          // 프론트엔드의 fee에 해당
    private String parkingFee;   // 프론트엔드의 parkingFee에 해당

    // Charger 엔티티로부터 DTO 생성
    public ChargerListResponseDTO(Charger charger) {
        this.id = charger.getStatId();
        this.name = charger.getStatNm();
        this.address = charger.getAddr();
        this.connector = charger.getChgerType();
        this.slot = charger.getSlot() != null ? charger.getSlot().toString() : null; // Integer를 String으로 변환
        this.speed = charger.getOutput();
        this.fee = charger.getChargingFee() != null ? charger.getChargingFee().toString() : null; // Double을 String으로 변환
        this.parkingFee = charger.getParkingFree();
    }
}
