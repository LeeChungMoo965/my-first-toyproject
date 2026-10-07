package com.portfolio.my_first_toyproject.common.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

import com.portfolio.my_first_toyproject.common.auth.jwt.dto.TokenInfoDTO;

@Component // 스프링이 이 클래스를 관리하도록 빈(Bean)으로 등록
public class JwtProvider {

    private final String secretKey; 
    private final long accessTokenValidTime = 30 * 60 * 1000L; // 30분
    private final long refreshTokenValidTime = 14 * 24 * 60 * 60 * 1000L; // 14일 

    // 생성자로 시크릿 키 주입
    public JwtProvider(@Value("${secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    // String 키를 JWT 전용 암호화 키로 변환
    public SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey); // 암호화 문자열 깨짐 방지와 정확한 데이터 관리를 위한 byte[]로 관리
        return Keys.hmacShaKeyFor(keyBytes);
    }

    
    public TokenInfoDTO createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // 권한들을 ','로 하나의 문자열로 합침 JSON형태로 단순한 형태로 바꿔야 하기 때문에 콜렉션 형태에서 하나의 문자열 형태로 변환 작업
        Date now = new Date();
        Date accessTokenExpiresIn = new Date(now.getTime() + accessTokenValidTime); 
        Date refreshTokenExpiresIn = new Date(now.getTime() + refreshTokenValidTime);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
        String refreshToken = Jwts.builder() 
                .setSubject(authentication.getName())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
        return new TokenInfoDTO("Bearer",accessToken ,refreshToken);// 로그인 검증 후 스프링 시큐리티에서 검증토큰 받고 우리 쪽 백에서 Access Token과 RefreshToken을 발급해주고, RefreshToken은 DB에 저장하고 AccessToken은 클라이언트에게 전달
        // TokenInfoDTO를 통해 AccessToken과 RefreshToken을 클라이언트에게 전달
        // AccessToken은 클라이언트에서 인증 헤더에 담아 요청 시 사용, RefreshToken은 만료 시 재발급 요청에 사용
        
    }
}