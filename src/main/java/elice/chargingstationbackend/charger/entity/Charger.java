package elice.chargingstationbackend.charger.entity;

import elice.chargingstationbackend.business.entity.BusinessOwner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Charger {

    @Id
    @Column(name = "stat_id", unique = true, nullable = false)
    private String statId;

    @Column(name = "stat_nm")
    private String statNm;

    @Column(name = "chger_id")
    private String chgerId;

    @Column(name = "chger_type")
    private String chgerType;

    @Column(name = "addr")
    private String addr;

    @Column(name = "detail_addr")
    private String detailAddr;  // 추가된 필드

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "use_time")
    private String useTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private BusinessOwner businessOwner;

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

    @Column(name = "parking_free")
    private String parkingFree;

    @Column(name = "limit_yn")
    private String limitYn;

    @Column(name = "limit_detail")
    private String limitDetail;

    @Column(name = "charging_fee")
    private Double chargingFee;

    @Column(name = "slot")
    private Integer slot;  // 추가된 필드
}
