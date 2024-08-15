package elice.chargingstationbackend.user.service;

import elice.chargingstationbackend.user.CustomUser;
import elice.chargingstationbackend.user.UserRepository;
import elice.chargingstationbackend.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var result = userRepository.findByEmail(email);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("이메일을 가진 유저 없음");
        }
        var user = result.get();  // 데이터베이스의 유저

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 역할에 따라 권한 설정
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }

        // CustomUser 객체 생성 (username은 닉네임, email은 실제 이메일)
        CustomUser customUser = new CustomUser(user.getEmail(), user.getPassword(), authorities, user.getUsername());

        return customUser;
    }
}
