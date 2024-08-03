package elice.chargingstationbackend.user.service;

import elice.chargingstationbackend.user.User;
import elice.chargingstationbackend.user.UserDto;
import elice.chargingstationbackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean existingEmail (String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean existingUsername (String username) {return userRepository.findByUsername(username).isPresent();}


    public void createAdminUser (String email, String password) {
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
        if (existingUsername(UserDto.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
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








}
