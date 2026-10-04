package com.portfolio.my_first_toyproject.member.dto;

public class MemberCreateRequest {
    
    private String email;
    private String password;
    private String name;

    // 기본 생성자 (스프링부트가 JSON을 객체로 변환할 때 꼭 필요합니다!)
    public MemberCreateRequest() {
    }

    // 데이터 확인용 Getter
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    // 데이터 저장용 Setter
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
}