package com.portfolio.my_first_toyproject.travelcourse.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TravelCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaceCategory placeCategory;

    @Column(nullable = false)
    private String title; // 제목

    @Column(nullable = false)
    private String summary; // 요약

    @Column(nullable = false)
    private String publicContent; // 공개 콘텐츠


    @Column(nullable = false)
    private int totalPrice ; // 총 가격
    
    // 양방향 매핑 (코스 안에 여러 장소들이 포함됨)
    @OneToMany(mappedBy = "travelCourse", cascade = CascadeType.ALL)
    private List<CoursePlace> places = new ArrayList<>();
    
    @Builder 
    public TravelCourse(PlaceCategory placeCategory, String title, String summary, String publicContent, int totalPrice) {
        this.placeCategory = placeCategory;
        this.title = title;
        this.summary = summary;
        this.publicContent = publicContent;
        this.totalPrice = totalPrice;
    }
}