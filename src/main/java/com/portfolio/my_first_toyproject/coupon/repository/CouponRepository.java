package com.portfolio.my_first_toyproject.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portfolio.my_first_toyproject.coupon.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
