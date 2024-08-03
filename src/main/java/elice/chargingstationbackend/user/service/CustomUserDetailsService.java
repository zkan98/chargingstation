package elice.chargingstationbackend.user.service;

import elice.chargingstationbackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var result = userRepository.findByEmail(email);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("이메일을 가진 유저 없음");
        }
        var user = result.get();    //테이블의 유저

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        else {
            authorities.add(new SimpleGrantedAuthority("User"));
        }
        //                      요 위치가 username , password   ,   권한, 추가된게 닉네임 = username(테이블)
        CustomUser a = new CustomUser(user.getEmail(), user.getPassword(), authorities);
        a.nickname = user.getUsername();

        return a; //이게 auth 에
    }
}
//괜히 헷갈리게 됐는데...  기본적으로 중복 불가능의 유저식별 은 이메일, Username 은 닉네임의 역할
// customUser 의 getUsername 을 하면 이메일이 나오는 것 , getNickname = username 나옴


