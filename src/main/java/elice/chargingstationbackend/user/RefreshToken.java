package elice.chargingstationbackend.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Date expiryDate;

}
