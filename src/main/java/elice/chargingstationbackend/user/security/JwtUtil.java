package elice.chargingstationbackend.user.security;


import elice.chargingstationbackend.user.service.CustomUser;
import elice.chargingstationbackend.user.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));//키 설정

    // 토큰생성
    public static String createToken(Authentication auth) {

        CustomUser user = (CustomUser) auth.getPrincipal();
        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));
                                            //문자형태로
            String jwt = Jwts.builder()
                    .claim("username", user.getUsername())  //이메일
                    .claim("nickname", user.getNickname())  //테이블 상 username, 닉네임
                    .claim("authorities", authorities)      //권한 설정
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 10000)) //유효기간 10초
                    .signWith(key)
                    .compact();
            return jwt;

    }


    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}

//refresh token -> 기존 토큰이 털렸나,
//access 토큰이 만료 후에,
