package elice.chargingstationbackend.maps.DTO;

import elice.chargingstationbackend.maps.Entity.ChargeStationAPI;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChargeStationDTO {
    private String statNm;
    private String chgerType;
    private String addr;
    private double lat;
    private double lng;
    private String useTime;
    private String busiCall;
    private Integer stat;
    private String powerType;
    private String parkingFree;
    private String note;
    private double distance;
    private String limitYn;
    private String limitDetail;

    public ChargeStationAPI DtoToEntity(ChargeStationDTO chargeStationDTO){
        return ChargeStationAPI.builder()
                .statNm(chargeStationDTO.getStatNm())
                .chgerType(chargeStationDTO.getChgerType())
                .addr(chargeStationDTO.getAddr())
                .lat(chargeStationDTO.getLat())
                .lng(chargeStationDTO.getLng())
                .useTime(chargeStationDTO.getUseTime())
                .busiCall(chargeStationDTO.getBusiCall())
                .stat(chargeStationDTO.getStat())
                .powerType(chargeStationDTO.getPowerType()).parkingFree(chargeStationDTO.getParkingFree())
                .note(chargeStationDTO.getNote())
                .limitYn(chargeStationDTO.getLimitYn()).limitDetail(chargeStationDTO.getLimitDetail())
                .build();
    }
}

