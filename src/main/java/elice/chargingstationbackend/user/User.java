package elice.chargingstationbackend.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //중복된 이메일 가입 방지
    private String email; // 이걸로 로그인

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;   // 이건 댓글 같은걸로 보이는 닉


    @Column(nullable = false, name = "admin")
    private boolean isAdmin;

    @Column(nullable = true)
    private String address;


    @Column(nullable = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ConnectorType connectorType;


    public enum ConnectorType {
        SLOW, DC_COMBO, CHADEMO, AC_THREE_PHASE, TESLA, PORTABLE, WIRELESS
    }
}
