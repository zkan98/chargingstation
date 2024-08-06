package elice.chargingstationbackend.charger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity @Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Charger {

    @Id
    @Column(name = "stat_id", unique = true, nullable = false)
    private String statId;

    @PrePersist
    public void prePersist() {
        if (this.statId == null || this.statId.isEmpty()) {
            this.statId = UUID.randomUUID().toString();
        }
    }

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "owner_id")
    // private BusinessOwner businessOwner;

    @Column(name = "stat_nm")
    private String statNm;

    @Column(name = "chger_id")
    private String chgerId;

    @Column(name = "chger_type")
    private String chgerType;

    @Column(name = "addr")
    private String addr;

    @Column(name = "power_type")
    private String powerType;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "use_time")
    private String useTime;

    @Column(name = "busi_id")
    private String busiId;

    @Column(name = "bnm")
    private String bnm;

    @Column(name = "busi_nm")
    private String busiNm;

    @Column(name = "busi_call")
    private String busiCall;

    @Column(name = "stat")
    private String stat;

    @Column(name = "stat_upd_dt")
    private String statUpdDt;

    @Column(name = "output")
    private String output;

    @Column(name = "method")
    private String method;

    @Column(name = "zcode")
    private String zcode;

    @Column(name = "zscode")
    private String zscode;

    @Column(name = "kind")
    private String kind;

    @Column(name = "kind_detail")
    private String kindDetail;

    @Column(name = "speed")
    private String speed;

    @Column(name = "limit_yn")
    private String limitYn;

    @Column(name = "limit_detail")
    private String limitDetail;

    @Column(name = "charging_fee")
    private Double chargingFee;

    @Column(name = "parking_free")
    private String parkingFree;
}
