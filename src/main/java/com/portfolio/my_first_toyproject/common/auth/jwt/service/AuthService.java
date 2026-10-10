package com.portfolio.my_first_toyproject.common.auth.jwt.service;

import com.portfolio.my_first_toyproject.common.auth.jwt.JwtProvider;
import com.portfolio.my_first_toyproject.common.auth.jwt.dto.TokenInfoDTO;
import com.portfolio.my_first_toyproject.common.auth.jwt.entity.RefreshToken;
import com.portfolio.my_first_toyproject.common.auth.jwt.repository.RefreshTokenRepository;
import com.portfolio.my_first_toyproject.member.domain.Member;
import com.portfolio.my_first_toyproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenInfoDTO login(String email, String password) {
        // 1. 사용자 인증
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 2. JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                member.getEmail(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()))
        );
        TokenInfoDTO tokenInfo = jwtProvider.createToken(authentication);

        // 3. Refresh Token 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(member.getId())
                .token(tokenInfo.getRefreshToken())
                .build();
        refreshTokenRepository.findByUserId(member.getId())
                .ifPresentOrElse(
                        existingToken -> existingToken.updateToken(tokenInfo.getRefreshToken()),
                        () -> refreshTokenRepository.save(refreshToken)
                );

        return tokenInfo;
    }    
}
