package elice.chargingstationbackend.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.equals("/users/login") || path.equals("/users/register");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // HttpServletRequest에서 토큰을 추출
        String jwtToken = jwtUtil.extractTokenFromRequest(request);

        if (jwtToken == null || jwtToken.isEmpty()) {
            System.out.println("Authorization 헤더가 없거나 토큰이 없습니다."); // 디버깅 로그 추가
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // JWT 토큰 검증 및 클레임 추출
            Claims claims = jwtUtil.extractToken(jwtToken); // jwtUtil의 메서드 사용
            System.out.println("토큰이 유효합니다. 사용자: " + claims.getSubject());

            // 사용자 정보를 설정 (Spring Security Context에 인증 정보 저장)
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                claims.getSubject(), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            System.err.println("토큰이 만료되었습니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("토큰이 만료되었습니다. 새로 로그인해주세요.");
            return;
        } catch (Exception e) {
            // JWT 서명 또는 구조 오류
            System.err.println("토큰 검증에 실패했습니다: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("토큰 검증에 실패했습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
