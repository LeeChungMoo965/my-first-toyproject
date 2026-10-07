package com.portfolio.my_first_toyproject.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberResponse {
    private final boolean success;
    private final String code;
    private final String message;
    private final String memberStatus;
    private final String id;
    private final String redirectUrl;
}   
