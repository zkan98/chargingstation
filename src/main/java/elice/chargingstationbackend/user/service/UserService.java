package elice.chargingstationbackend.user.service;

import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.user.UserDto;
import elice.chargingstationbackend.user.UserRepository;
import elice.chargingstationbackend.user.Role;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public boolean existingEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean existingUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void createAdmin(String email, String password) {
        User adminUser = new User();
        adminUser.setEmail(email);
        adminUser.setPassword(passwordEncoder.encode(password));
        adminUser.getRoles().add(Role.ROLE_ADMIN);
        userRepository.save(adminUser);
    }

    public void createUser(UserDto userDto) {
        if (existingEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setConnectorType(userDto.getConnectorType());

        // roles가 null이면 초기화
        if (userDto.getRoles() == null) {
            userDto.setRoles(new HashSet<>(Collections.singletonList(Role.ROLE_USER))); // 기본값 설정
        }

        user.setRoles(new HashSet<>(userDto.getRoles()));

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

        SecurityContextHolder.getContext().setAuthentication(auth);

        return auth;
    }

    @Transactional
    public User updateUser(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAddress(userDto.getAddress());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setConnectorType(userDto.getConnectorType());
        user.getRoles().clear();
        user.getRoles().addAll(userDto.getRoles());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        userRepository.delete(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setUsername(user.getUsername());
        userDto.setAddress(user.getAddress());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setConnectorType(user.getConnectorType());
        userDto.setRoles(user.getRoles()); // User의 역할들을 설정
        return userDto;
    }
}
