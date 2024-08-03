package elice.chargingstationbackend.user.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    public String nickname;
    public CustomUser (String  username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities)
    { super(username, password, authorities);}
}
