package com.portfolio.my_first_toyproject.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import com.portfolio.my_first_toyproject.member.entity.Member; // 본인의 Member 엔티티 경로

import com.portfolio.my_first_toyproject.member.domain.Member;

// 💡 JpaRepository<엔티티 클래스, PK 데이터 타입>
public interface MemberRepository extends JpaRepository<Member, Long> {
}