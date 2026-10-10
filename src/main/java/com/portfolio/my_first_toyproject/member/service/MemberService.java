package com.portfolio.my_first_toyproject.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.my_first_toyproject.member.domain.Member;
import com.portfolio.my_first_toyproject.member.domain.Role;
import com.portfolio.my_first_toyproject.member.dto.MemberCreateRequest;
import com.portfolio.my_first_toyproject.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void addMember(MemberCreateRequest request){
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .name(request.getName())
                .role(Role.BOSS) // 사장님 가입이니까 기본 권한 셋팅
                .build();
        memberRepository.save(member);
    }
}
    