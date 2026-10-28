package com.portfolio.my_first_toyproject.travelcourse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.my_first_toyproject.travelcourse.domain.TravelCourse;
import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseCreateRequest;
import com.portfolio.my_first_toyproject.travelcourse.repository.TravelCourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelCourseService {
    private final TravelCourseRepository travelCourseRepository;

    @Transactional
    public Long createTravelCourse(TravelCourseCreateRequest request){
        TravelCourse travelCourse = TravelCourse.builder()
            .placeCategory(request.getPlaceCategory())
            .title(request.getTitle())
            .summary(request.getSummary())
            .publicContent(request.getPublicContent())
            .totalPrice(request.getTotalPrice())
            .build();

        TravelCourse savedTravelCourse = travelCourseRepository.save(travelCourse);
        return savedTravelCourse.getId();
    }
}
