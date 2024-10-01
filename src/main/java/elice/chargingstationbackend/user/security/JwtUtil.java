package elice.chargingstationbackend.user.security;

import elice.chargingstationbackend.user.CustomUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // SecretKey는 외부에서 접근하지 않도록 private으로 변경합니다.
    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(
        "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"));

    public String createAccessToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();

        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = new Date(currentTimeMillis + 1000 * 60 * 10); // 10분 후

        // 디버깅을 위한 로그 출력
        System.out.println("현재 시간 (밀리초): " + currentTimeMillis);
        System.out.println("발급 시간 (Date): " + issuedAt);
        System.out.println("만료 시간 (Date): " + expiration);

        return Jwts.builder()
            .setSubject(user.getUsername())
            .claim("authorities", auth.getAuthorities())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(key, SignatureAlgorithm.HS384)
            .compact();
    }

    // JWT 토큰에서 Claims 추출
    public Claims extractToken(String token) {
        try {
            System.out.println("검증할 토큰: " + token); // 토큰 디버깅 로그 추가
            return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            System.err.println("JWT 토큰이 만료되었습니다.");
            throw e;
        } catch (Exception e) {
            System.err.println("JWT 토큰 검증 실패: " + e.getMessage());
            throw new RuntimeException("JWT 검증 오류");
        }
    }

    // Authorization 헤더에서 Bearer 토큰 추출
    public String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println("Authorization 헤더: " + bearerToken); // 헤더 내용 디버깅 로그 추가
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            System.out.println("추출된 토큰: " + token); // 추출된 토큰 디버깅 로그 추가
            return token;
        }
        return null;
    }
}
