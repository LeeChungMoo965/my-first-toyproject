package com.portfolio.my_first_toyproject.common.auth.jwt.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenInfoDTO {
    
    private final String grantType;
    
    private final String accessToken;
    
    private final String refreshToken;
    
}