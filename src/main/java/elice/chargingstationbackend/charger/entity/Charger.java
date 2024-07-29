package elice.chargingstationbackend.charger.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Charger {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charger_id")
    private Long id;

    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "owner_id")
    // private Owner owner;

    @Column
    private String chargerName;

    @Column
    private String address;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = false)
    private int slots;

    @Column(nullable = false)
    private int availableSlots;

    @Column(nullable = false)
    private String connectorType;

    @Column(nullable = false)
    private double chargingSpeed;

    @Column(nullable = false)
    private double chargingFee;

    @Column(nullable = false)
    private int parkingFee;
}
