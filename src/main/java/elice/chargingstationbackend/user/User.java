package elice.chargingstationbackend.user;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED) // 상속 전략 설정 (JOINED 전략 사용)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // 중복된 이메일 가입 방지
    private String email; // 로그인에 사용되는 이메일

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username; // 사용자 이름 또는 닉네임

    @Column(nullable = true)
    private String address; // 주소

    @Column(nullable = true)
    private String phoneNumber; // 전화번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ConnectorType connectorType; // 커넥터 유형

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>(); // 필드를 초기화


    public enum ConnectorType {
        SLOW, DC_COMBO, CHADEMO, AC_THREE_PHASE, TESLA, PORTABLE, WIRELESS
    }

    @Transient
    private Collection<GrantedAuthority> authorities; // 권한 정보 (Spring Security)
}
