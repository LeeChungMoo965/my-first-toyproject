package com.portfolio.my_first_toyproject.common.auth.jwt.filter;
import com.portfolio.my_first_toyproject.common.auth.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // 이 클래스는 JWT 인증을 처리하는 필터로, 요청이 들어올 때마다 한 번씩 실행됩니다.
    // 실제 구현은 생략되어 있으며, 필요에 따라 JWT 토큰을 검증하고 인증 정보를 설정하는 로직을 추가해야 합니다.
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws java.io.IOException, ServletException {
        // JWT 토큰 검증 및 인증 정보 설정 로직을 여기에 구현해야 합니다.
        // 예: 토큰 추출, 유효성 검사, 인증 객체 생성 등
        String token = resolveToken(request); // 요청에서 JWT 토큰을 추출하는 메서드 호출
        if (token != null && jwtProvider.validateToken(token)) {    
            Authentication authentication = jwtProvider.getAuthentication(token); // 토큰에서 인증 정보를 가져오는 메서드 호출
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 정보를 SecurityContext에 설정
        }
        filterChain.doFilter(request, response); // 다음 필터로 요청을 전달
    }

    private String resolveToken(HttpServletRequest request) {
        // 요청 헤더에서 JWT 토큰을 추출하는 로직을 구현해야 합니다.
        // 예: "Authorization" 헤더에서 "Bearer " 접두사를 제거하고 토큰만 반환
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 접두사 제거
        }
        return null; // 실제 구현 필요
    }
}
