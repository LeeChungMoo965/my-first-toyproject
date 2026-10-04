package com.portfolio.my_first_toyproject.member.dto;

import lombok.Data;

@Data
public class MemberResponse {
    private final boolean success;
    private final String code;
    private final String message;
    private final String memberStatus;
    private final String id;
    private final String redirectUrl;

    public MemberResponse(
            boolean success,
            String code,
            String message,
            String memberStatus,
            String id,
            String redirectUrl) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.memberStatus = memberStatus;
        this.id = id;
        this.redirectUrl = redirectUrl;
    }
}   
