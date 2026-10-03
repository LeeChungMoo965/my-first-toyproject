package com.portfolio.my_first_toyproject.employment.domain;

import com.portfolio.my_first_toyproject.member.domain.Member;
import com.portfolio.my_first_toyproject.store.domain.Store;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer hourlyWage; // 시급

    @Column(nullable = false, length = 20)
    private String status; // 재직 상태 (ACTIVE: 재직, INACTIVE: 퇴사)
}