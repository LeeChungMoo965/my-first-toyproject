package com.portfolio.my_first_toyproject.common.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import com.portfolio.my_first_toyproject.common.auth.jwt.dto.CustomUser;
import com.portfolio.my_first_toyproject.common.auth.jwt.dto.TokenInfoDTO;
@Slf4j
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
                .claim("userId", ((CustomUser) authentication.getPrincipal()).getUserId()) // CustomUser에서 userId를 가져와 claim에 추가
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
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String auth = Optional.ofNullable(claims.get("auth", String.class))
                .orElseThrow(() -> new IllegalArgumentException("권한 정보가 없는 토큰입니다."));
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(auth.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        Long userId = claims.get("userId", Long.class); // CustomUser에서 userId를 가져와 claim에서 추출
        UserDetails principal = new CustomUser(userId, claims.getSubject(), "",authorities); // principal은 사용자 정보, authorityList는 권한 정보
        return new UsernamePasswordAuthenticationToken(principal,"",authorities); // principal은 사용자 정보, authorityList는 권한 정보

    }
    public boolean validateToken(String token) {
        try {
            getClaims(token); // 토큰의 유효성을 검증하기 위해 Claims를 가져옴. 만약 토큰이 유효하지 않으면 예외가 발생
            return true;
        } catch (io.jsonwebtoken.security.SecurityException e) {
                log.debug("[SecurityException]잘못된 JWT 서명입니다.");
                throw new JwtException("[SecurityException] 잘못된 JWT 서명입니다.");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            log.debug("[ExpiredJwtException] 토큰 만료");
            throw new JwtException("[ExpiredJwtException] 토큰 만료");
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            log.debug("[UnsupportedJwtException]지원되지 않는 JWT 토큰입니다.");
            throw new JwtException("[UnsupportedJwtException] 지원되지 않는 JWT 토큰입니다.");
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            log.debug("[MalformedJwtException] JWT 토큰이 잘못되었습니다.");
            throw new JwtException("[MalformedJwtException] JWT 토큰이 잘못되었습니다.");
        } catch (IllegalArgumentException e) {
            log.debug("[IllegalArgumentException] JWT 토큰이 잘못되었습니다.");
            throw new JwtException("[IllegalArgumentException] JWT 토큰이 잘못되었습니다.");
        }
            
    }
}
