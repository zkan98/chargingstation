package elice.chargingstationbackend.maps.mapsDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
