package elice.chargingstationbackend.user;


import elice.chargingstationbackend.user.security.JwtUtil;
import elice.chargingstationbackend.user.service.CustomUser;
import elice.chargingstationbackend.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/login")
    public String loginPage () { return "/login.html";}

    @GetMapping("/register")
    public String registerPage() { return "/register.html"; }

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

    @GetMapping("/myPage")
    @ResponseBody
    public String myPage(Authentication auth) {

        var user = (CustomUser) auth.getPrincipal();

        return "";
    }

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


    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody Map<String, String> data, HttpServletResponse response) {

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("email"), data.get("password")
        );
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth); // auth 변수에 유저정보추가
        //                                              이게 최신 auth 와 동일
        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());


        var cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(600);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie((cookie));

//r개발자도구, 앱, 쿠키
        return jwt;
    }




}
