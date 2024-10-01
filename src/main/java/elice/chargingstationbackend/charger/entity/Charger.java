package elice.chargingstationbackend.charger.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "use_time")
    private String useTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonBackReference(value = "owner-chargers")
    private BusinessOwner businessOwner;

    @OneToMany(mappedBy = "charger", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "charger-reviews")
    private List<Review> reviews;

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
}
