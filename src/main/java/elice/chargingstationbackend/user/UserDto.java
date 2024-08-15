package elice.chargingstationbackend.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String address;
    private String phoneNumber;
    private User.ConnectorType connectorType;
    private Set<Role> roles; // 추가된 역할 필드
}
