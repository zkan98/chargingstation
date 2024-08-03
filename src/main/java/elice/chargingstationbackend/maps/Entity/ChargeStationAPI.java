package elice.chargingstationbackend.maps.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ChargeStationAPI", indexes = {
        @Index(name = "idx_lat_lng", columnList = "lat, lng")
})
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

    @Column(name = "powerType", columnDefinition="VARCHAR(255)")
    private String powerType;

    @Column(name = "parkingFree", columnDefinition="VARCHAR(1)")
    private String parkingFree;

    @Column(name = "note", columnDefinition="VARCHAR(255)")
    private String note;

    @Column(name = "limitYn", columnDefinition="VARCHAR(1)")
    private String limitYn;

    @Column(name = "limitDetail", columnDefinition="VARCHAR(255)")
    private String limitDetail;

    @Builder
    public ChargeStationAPI(String statNm, String chgerType, String addr, double lat, double lng, String useTime,
                            String busiCall, Integer stat, String powerType, String parkingFree, String note, String limitYn,
                            String limitDetail){
        this.statNm = statNm;
        this.chgerType = chgerType;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
        this.useTime = useTime;
        this.busiCall = busiCall;
        this.stat = stat;
        this.powerType = powerType;
        this.parkingFree = parkingFree;
        this.note = note;
        this.limitYn = limitYn;
        this.limitDetail = limitDetail;
    }
}