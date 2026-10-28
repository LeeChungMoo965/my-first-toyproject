package com.portfolio.my_first_toyproject.coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.portfolio.my_first_toyproject.member.domain.Member;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 유저가 가진 쿠폰인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 어떤 종류의 쿠폰인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    // 🔥 가장 중요한 필드: 사용 여부 (이게 있어야 중복 할인을 막습니다!)
    @Column(nullable = false)
    private boolean isUsed = false; 

    // 언제 사용했는지 기록 (결제 로직 추적용)
    private LocalDateTime usedAt; 

    @Builder
    public MemberCoupon(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = false;
    }

    // ==========================================
    // [비즈니스 로직] 쿠폰 사용 처리
    // ==========================================
    public void use() {
        if (this.isUsed) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
        this.isUsed = true;
        this.usedAt = LocalDateTime.now();
    }
}