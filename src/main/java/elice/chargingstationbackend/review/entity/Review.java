package elice.chargingstationbackend.review.entity;

import elice.chargingstationbackend.charger.entity.Charger;
import elice.chargingstationbackend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    private Charger charger;

    @Column(nullable = false)
    private double rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String timestamp;
}
