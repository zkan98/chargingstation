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
    private Long chargestationId;

    @Column(name = "statNm", columnDefinition="VARCHAR(255)")
    private String statNm;

    @Column(name = "statId", columnDefinition="bigint(20)")
    private Long statId;

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

    @Column(name = "busiId", columnDefinition="VARCHAR(10)")
    private String busiId;

    @Column(name = "bnm", columnDefinition="VARCHAR(255)")
    private String bnm;

    @Column(name = "busiNm", columnDefinition="VARCHAR(255)")
    private String busiNm;

    @Column(name = "busiCall", columnDefinition="VARCHAR(20)")
    private String busiCall;

    @Column(name = "stat", columnDefinition="INT")
    private Integer stat;

    @Column(name = "statUpdDt", columnDefinition="VARCHAR(20)")
    private String statUpdDt;

    @Column(name = "lastTsd", columnDefinition="VARCHAR(20)")
    private String lastTsd;

    @Column(name = "lastTed", columnDefinition="VARCHAR(20)")
    private String lastTed;

    @Column(name = "nowTsd", columnDefinition="VARCHAR(20)")
    private String nowTsd;

    @Column(name = "powerType", columnDefinition="VARCHAR(255)")
    private String powerType;

    @Column(name = "output", columnDefinition="VARCHAR(10)")
    private String output;

    @Column(name = "method", columnDefinition="VARCHAR(10)")
    private String method;

    @Column(name = "zcode", columnDefinition="INT")
    private Integer zcode;

    @Column(name = "zscode", columnDefinition="INT")
    private Integer zscode;

    @Column(name = "kind", columnDefinition="VARCHAR(10)")
    private String kind;

    @Column(name = "kindDetail", columnDefinition="VARCHAR(10)")
    private String kindDetail;

    @Column(name = "parkingFree", columnDefinition="VARCHAR(1)")
    private String parkingFree;

    @Column(name = "note", columnDefinition="VARCHAR(255)")
    private String note;

    @Column(name = "limitYn", columnDefinition="VARCHAR(1)")
    private String limitYn;

    @Column(name = "limitDetail", columnDefinition="VARCHAR(255)")
    private String limitDetail;

    @Column(name = "delYn", columnDefinition="VARCHAR(1)")
    private String delYn;

    @Column(name = "delDetail", columnDefinition="VARCHAR(255)")
    private String delDetail;

    @Column(name = "trafficYn", columnDefinition="VARCHAR(1)")
    private String trafficYn;

    @Builder
    public ChargeStationAPI(String statNm, String chgerType, String addr, double lat, double lng, String useTime,
                            String busiCall, Integer stat, String powerType, String parkingFree, String note){
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
    }
}