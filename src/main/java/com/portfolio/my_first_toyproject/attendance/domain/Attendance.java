package com.portfolio.my_first_toyproject.attendance.domain;

import com.portfolio.my_first_toyproject.employment.domain.Employment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 수많은 출퇴근 기록(다)은 하나의 고용 관계(일)에 속하므로 다대일
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id", nullable = false)
    private Employment employment;

    @Column(nullable = false)
    private LocalDateTime clockIn; // 출근 시간

    @Column // 퇴근 전까지는 데이터가 비어있어야 하므로 nullable = false를 쓰지 않음 (Null 허용)
    private LocalDateTime clockOut; // 퇴근 시간 
}   