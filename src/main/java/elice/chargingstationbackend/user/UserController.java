package elice.chargingstationbackend.user;


import elice.chargingstationbackend.user.security.JwtUtil;
import elice.chargingstationbackend.user.service.RefreshTokenService;
import elice.chargingstationbackend.user.service.UserService;
import elice.chargingstationbackend.user.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;



//    private User convertToUser(CustomUser customUser, Collection<? extends GrantedAuthority> authorities) {
//        User user = new User();
//        user.setEmail(customUser.getUsername());
//        user.setPassword(customUser.getPassword());
//        user.setUsername(customUser.getNickname());
//        user.setAuthorities((Collection<GrantedAuthority>) authorities);
//
//        return user;
//    }

//    @GetMapping("/login")
//    public String loginPage () { return "/login.html";}
//
//    @GetMapping("/register")
//    public String registerPage() { return "/register.html"; }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto UserDto) {
        try {
            userService.createUser(UserDto);
            return ResponseEntity.ok(Collections.singletonMap("message", "회원 가입이 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("reason", e.getMessage()));
        }
    }

//    @GetMapping("/myPage")
//    @ResponseBody
//    public String myPage(Authentication auth) {
//
//        var user = (CustomUser) auth.getPrincipal();
//
//        return "";
//    }

    /*  리액트로 구성하는 프론트의 경우 아래와 같이 JSON 형식으로 정보를 짜서 보내주자.
    @GetMapping("/api/user")
    public ResponseEntity<Map<String, Object>> getUser(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            User user = (User) auth.getPrincipal();
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", user.getUsername());
            userInfo.put("email", user.getEmail());
            // 다른 프론트로 넘길 정보 추가해서
            return ResponseEntity.ok(userInfo);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
     */
    @GetMapping("/info")
    public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            try {
                String email = jwtUtil.getEmailFromToken(jwt);
                if (email != null) {
                    Optional<User> optionalUser = userRepository.findByEmail(email);
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        UserDto userDTO = new UserDto();
                        userDTO.setId(user.getId());
                        userDTO.setEmail(user.getEmail());
                        userDTO.setUsername(user.getUsername());
                        userDTO.setAdmin(user.isAdmin());
                        userDTO.setAddress(user.getAddress());
                        userDTO.setPhoneNumber(user.getPhoneNumber());
                        userDTO.setConnectorType(user.getConnectorType());
                        return ResponseEntity.ok(userDTO);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> userData, HttpServletResponse response) {
        String email = userData.get("email");
        String password = userData.get("password");

        try {
            Authentication auth = userService.authenticate(email, password);
            Object principal = auth.getPrincipal();

            if (principal instanceof CustomUser customUser) {

                User user = userRepository.findByEmail(customUser.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + customUser.getUsername()));

                String accessToken = JwtUtil.createAccessToken(auth);

                RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
                //있는지 확인하고 있으면 지우고 만들고 없으며 그냥 생성
                // ㄱ 컴에서 접속중, refreshToken 존재, 이때 ㄴ 컴에서 접속시 refreshToken 이 있는데 또 생성
                // 왜? ㄴ컴의 쿠키에는 토큰이 없으니까

                // 쿠키에 Refresh Token 저장
                Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getToken());
                refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일 유효기간
                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setPath("/");
                response.addCookie(refreshTokenCookie);

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("message", "로그인되었습니다.");
                responseData.put("accessToken", accessToken); // 응답 바디에 Access Token 포함

                return ResponseEntity.ok(responseData);
            } else {
                throw new IllegalArgumentException("Unexpected principal type");
            }
        } catch (UsernameNotFoundException | IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "잘못된 이메일 또는 비밀번호입니다.");
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 없습니다.");
        }

        try {
            Claims claims = JwtUtil.extractToken(refreshToken);
            String username = claims.get("username", String.class);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            String newAccessToken = JwtUtil.createAccessToken(auth);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("accessToken", newAccessToken);

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 Refresh Token입니다.");
        }
    }


    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // 쿠키 무효화
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);

        String refreshToken = refreshTokenService.getRefreshTokenFromRequest(request);
        if (refreshToken != null) {
            refreshTokenRepository.deleteByToken(refreshToken);
        }

        Map<String, String> responseData = new HashMap<>();
        responseData.put("message", "로그아웃되었습니다.");

        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/mypage/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDTO, @RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            try {
                String email = jwtUtil.getEmailFromToken(jwt);
                if (email != null) {
                    User updatedUser = userService.updateUser(email, userDTO);
                    return ResponseEntity.ok(userDTO);
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @DeleteMapping("/mypage/delete")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            try {
                String email = jwtUtil.getEmailFromToken(jwt);
                if (email != null) {
                    userService.deleteUser(email);
                    return ResponseEntity.noContent().build();
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }





}

