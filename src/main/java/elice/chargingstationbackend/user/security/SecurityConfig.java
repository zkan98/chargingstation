package elice.chargingstationbackend.user.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf.disable());
        http.authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers("/**").permitAll());
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
//        http.formLogin( (formLogin) ->
//                    formLogin.loginPage("/login")
//                            .defaultSuccessUrl("/"));
        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);
        http.logout(logout -> logout.logoutUrl("/logout"));  // 이 URL 로 GET 요청 보내면 로그아웃

        return http.build();
    }


}
