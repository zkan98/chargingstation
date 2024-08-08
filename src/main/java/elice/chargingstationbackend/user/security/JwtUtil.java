package elice.chargingstationbackend.user.security;


import elice.chargingstationbackend.user.CustomUser;
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
    // Access Token 생성
    public static String createAccessToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("nickname", user.getNickname())
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(key)
                .compact();
    }




    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims;
    }
}

//refresh token -> 기존 토큰이 털렸나,
//access 토큰이 만료 후에,

// Refresh Token 생성
//    public static String createRefreshToken(Authentication auth) {
//        CustomUser user = (CustomUser) auth.getPrincipal();
//        return Jwts.builder()
//                .claim("username", user.getUsername())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일 유효기간
//                .signWith(key)
//                .compact();
//    }
