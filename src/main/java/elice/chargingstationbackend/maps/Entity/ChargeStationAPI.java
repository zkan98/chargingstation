package elice.chargingstationbackend.maps.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ChargeStationAPI")
@NoArgsConstructor
@AllArgsConstructor
public class ChargeStationAPI {
    @Id
    @Column(name = "chargestationId", columnDefinition = "bigint(20)")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargestationId;

    @Column(name = "statNm", columnDefinition="VARCHAR(255)")
    private String statNm;

    @Column(name = "chgerType", columnDefinition="VARCHAR(10)")
    private String chgerType;

    @Column(name = "addr", columnDefinition="VARCHAR(255)")
    private String addr;

    @Column(name = "lat", columnDefinition="DOUBLE")
    private Double lat;

    @Column(name = "lng", columnDefinition="DOUBLE")
    private Double lng;

    @Column(name = "useTime", columnDefinition="VARCHAR(255)")
    private String useTime;

    @Column(name = "busiCall", columnDefinition="VARCHAR(20)")
    private String busiCall;

    @Column(name = "stat", columnDefinition="INT")
    private Integer stat;

    @Column(name = "parkingFree", columnDefinition="VARCHAR(1)")
    private String parkingFree;

    @Column(name = "limitYn", columnDefinition="VARCHAR(1)")
    private String limitYn;

    @Builder
    public ChargeStationAPI(String statNm, String chgerType, String addr, double lat, double lng, String useTime,
                            String busiCall, Integer stat, String parkingFree, String limitYn){
        this.statNm = statNm;
        this.chgerType = chgerType;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
        this.useTime = useTime;
        this.busiCall = busiCall;
        this.stat = stat;
        this.parkingFree = parkingFree;
        this.limitYn = limitYn;
    }

}