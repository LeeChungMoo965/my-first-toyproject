package com.portfolio.my_first_toyproject.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.my_first_toyproject.member.domain.Member;
import com.portfolio.my_first_toyproject.member.domain.Role;
import com.portfolio.my_first_toyproject.member.dto.MemberCreateRequest;
import com.portfolio.my_first_toyproject.member.repository.MemberRepository;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public void addMember(MemberCreateRequest request){
        Member member = Member.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // 나중엔 여기에 암호화 로직이 들어갑니다
                .name(request.getName())
                .role(Role.BOSS) // 사장님 가입이니까 기본 권한 셋팅
                .build();
        memberRepository.save(member);
    }
}
