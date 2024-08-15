package elice.chargingstationbackend.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String address;
    private String phoneNumber;
    private User.ConnectorType connectorType;
    private Set<Role> roles;
    private String userType;

    // 비즈니스 오너 관련 필드 추가
    private String businessId;
    private String businessName;
    private String businessCall;
    private String businessCorporateName;
}
