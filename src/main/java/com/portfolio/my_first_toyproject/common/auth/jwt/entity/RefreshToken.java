package com.portfolio.my_first_toyproject.common.auth.jwt.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 기본 생성자는 필수, 안전하게 PROTECTED로 설정
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    // 어떤 유저의 토큰인지 식별 (단일 기기 로그인만 허용한다면 unique = true 추가)
    @Column(name = "user_id", nullable = false, unique = true) 
    private Long userId;

    // 실제 Refresh Token 문자열 (JWT는 길이가 길 수 있으므로 넉넉하게 잡습니다)
    @Column(name = "token", nullable = false, length = 512) 
    private String token;

    @Builder
    public RefreshToken(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    // Refresh Token 갱신(Rotation)을 위한 비즈니스 메서드
    public void updateToken(String newToken) {
        this.token = newToken;
    }
}