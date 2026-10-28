package com.portfolio.my_first_toyproject.coupon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 쿠폰명 (예: 츠지한 덮밥 50% 선착순 할인권)

    private int discountAmount; // 할인 금액 또는 비율

    @Column(nullable = false)
    private int maxQuantity; // 발급 가능한 총 수량 (예: 100장)

    @Column(nullable = false)
    private int issuedQuantity = 0; // 현재까지 발급된 수량

    @Builder
    public Coupon(String name, int discountAmount, int maxQuantity) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.maxQuantity = maxQuantity;
    }

    // 비즈니스 로직: 선착순 발급 로직 (동시성 이슈가 발생할 핵심 메서드)
    public void issue() {
        if (this.issuedQuantity >= this.maxQuantity) {
            throw new RuntimeException("선착순 쿠폰이 모두 소진되었습니다.");
        }
        this.issuedQuantity++;
    }
}