package elice.chargingstationbackend.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String email;
    private String password;
    private String username;

    private boolean isAdmin;

    private String address;
    private String phoneNumber;
    private User.ConnectorType connectorType;

}
