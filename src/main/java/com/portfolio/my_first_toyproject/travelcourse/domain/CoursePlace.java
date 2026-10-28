package com.portfolio.my_first_toyproject.travelcourse.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoursePlace {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_course_id")
    private TravelCourse travelCourse; // 어떤 코스에 속해있는지 (FK)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory category; // RESTAURANT, ACTIVITY 등 (프론트 탭 분류용)

    @Column(nullable = false)
    private String name; // 예: "츠지한 본점", "유니버셜 스튜디오"

    private int price; // 개별 가격

    // 📍 지도 연동을 위한 위도/경도 (프론트에서 구글맵 띄울 때 필수)
    private double latitude; 
    private double longitude;

    private String peakHours; // 붐비는 시간대 (예: "11:30 - 13:00")

    @Column(columnDefinition = "TEXT")
    private String bloggerReview; // 작성자(블로거)의 상세 리뷰 및 꿀팁
}