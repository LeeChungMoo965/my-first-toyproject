package com.portfolio.my_first_toyproject.travelcourse.dto;

import com.portfolio.my_first_toyproject.travelcourse.domain.PlaceCategory;

import lombok.Getter;

@Getter
public class TravelCourseCreateRequest {
    private String title;
    private String summary;
    private String publicContent;
    private String premiumContent;
    private PlaceCategory placeCategory;
    private int totalPrice;
}