package elice.chargingstationbackend.user.service;

import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.user.UserDto;
import elice.chargingstationbackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public boolean existingEmail (String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean existingUsername (String username) {return userRepository.findByUsername(username).isPresent();}


    public void createAdmin (String email, String password) {
        User adminUser = new User();
        adminUser.setEmail(email);
        adminUser.setPassword(passwordEncoder.encode(password));
        adminUser.setAdmin(true);
        userRepository.save(adminUser);
    }

    public void createUser(UserDto UserDto) {
        if (existingEmail(UserDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }


        User user = new User();
        user.setEmail(UserDto.getEmail());
        user.setPassword(passwordEncoder.encode(UserDto.getPassword()));
        user.setUsername(UserDto.getUsername());
        user.setAdmin(UserDto.isAdmin());
        user.setAddress(UserDto.getAddress());
        user.setPhoneNumber(UserDto.getPhoneNumber());
        user.setConnectorType(UserDto.getConnectorType());

        userRepository.save(user);
    }

    public Authentication authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 이메일입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        var authToken = new UsernamePasswordAuthenticationToken(email, password);
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        // 이게 UserDetailsService를 이용하여 유저를 가져오며 거기서 권한정보를 가지고 오게 되는 것.

        SecurityContextHolder.getContext().setAuthentication(auth);
        //auth = SecurityContextHolder.getContext().getAuthentication()

        return auth;
    }








}
