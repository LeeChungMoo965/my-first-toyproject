package com.portfolio.my_first_toyproject.member.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.my_first_toyproject.member.dto.MemberCreateRequest;
import com.portfolio.my_first_toyproject.member.dto.MemberResponse;
import com.portfolio.my_first_toyproject.member.service.MemberService;

import org.glassfish.jaxb.core.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping(value = "/api/members")
public class MemberController {
    private final MemberService memberService;
    
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @PostMapping
    public ResponseEntity<Void> AddMember (@RequestBody MemberCreateRequest requestData) {
       
       memberService.addMember(requestData);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
}
