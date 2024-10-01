package elice.chargingstationbackend.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import elice.chargingstationbackend.business.entity.BusinessOwner;
import elice.chargingstationbackend.review.entity.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ConnectorType connectorType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-reviews")
    private List<Review> reviews;

    @Transient
    private Collection<GrantedAuthority> authorities;

    public enum ConnectorType {
        SLOW, DC_COMBO, CHADEMO, AC_THREE_PHASE, TESLA, PORTABLE, WIRELESS
    }

    public BusinessOwner getBusinessOwner() {
        if (this instanceof BusinessOwner) {
            return (BusinessOwner) this;
        } else {
            return null;
        }
    }
}
