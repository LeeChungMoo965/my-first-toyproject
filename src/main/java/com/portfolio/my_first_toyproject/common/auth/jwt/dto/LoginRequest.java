package com.portfolio.my_first_toyproject.common.auth.jwt.dto;

public record LoginRequest( 
    String email,
    String password
){
}