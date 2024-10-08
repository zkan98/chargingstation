package elice.chargingstationbackend.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    private String nickname;

    public CustomUser(String email, String password, Collection<? extends GrantedAuthority> authorities, String nickname) {
        super(email, password, authorities);
        this.nickname = nickname;
    }
}
