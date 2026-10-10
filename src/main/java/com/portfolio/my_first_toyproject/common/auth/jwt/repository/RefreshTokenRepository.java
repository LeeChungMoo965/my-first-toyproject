package com.portfolio.my_first_toyproject.common.auth.jwt.repository;

import com.portfolio.my_first_toyproject.common.auth.jwt.entity.RefreshToken; // 본인 패키지 경로에 맞게 임포트
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    // 유저 ID로 기존 발급된 Refresh Token이 있는지 찾을 때 사용할 메서드
    Optional<RefreshToken> findByUserId(Long userId);
}