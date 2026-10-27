package com.portfolio.my_first_toyproject.common.config;

// 1. 필요한 임포트 전부 추가!
import com.portfolio.my_first_toyproject.common.auth.jwt.JwtProvider;
import com.portfolio.my_first_toyproject.common.auth.jwt.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor // 2. 이게 있어야 final이 붙은 jwtProvider를 스프링이 알아서 주입해 줍니다!
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    
    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/members/signup", "/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                // 3. 임포트가 잘 되었으니 이제 빨간 줄 없이 정상 인식될 겁니다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
                
        return http.build();
    }
}