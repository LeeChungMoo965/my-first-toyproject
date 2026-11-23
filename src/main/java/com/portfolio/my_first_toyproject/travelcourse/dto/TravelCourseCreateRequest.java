package com.portfolio.my_first_toyproject.travelcourse.dto;

import com.portfolio.my_first_toyproject.travelcourse.domain.PlaceCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // JSON 데이터를 DTO로 변환할 때 필수!
@AllArgsConstructor
@Builder // 이따가 테스트 코드 짤 때 객체를 편하게 만들기 위해 추가
public class TravelCourseCreateRequest {
    
    private String title;
    private String summary;
    private String publicContent;
    private PlaceCategory placeCategory;
    private int totalPrice;
    
    // premiumContent는 엔티티에 없으므로 삭제했습니다!
}