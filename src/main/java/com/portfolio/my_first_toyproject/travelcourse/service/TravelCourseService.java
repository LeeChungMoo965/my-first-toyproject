package com.portfolio.my_first_toyproject.travelcourse.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portfolio.my_first_toyproject.travelcourse.domain.PlaceCategory;
import com.portfolio.my_first_toyproject.travelcourse.domain.TravelCourse;
import com.portfolio.my_first_toyproject.travelcourse.dto.CourseResultResponse;
import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseCreateRequest;
import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseResponse;
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
    @Transactional(readOnly = true)
    public List<TravelCourseResponse> getTravelCourse(String title, PlaceCategory category) {
        
        
        if(title != null && category != null) {
            List<TravelCourse> travelCoursesByTitleAndCategory = travelCourseRepository.findByTitleAndPlaceCategory(title, category);
            return travelCoursesByTitleAndCategory.stream()
                    .map(course -> TravelCourseResponse.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .summary(course.getSummary())
                        .publicContent(course.getPublicContent())
                        .placeCategory(course.getPlaceCategory())
                        .totalPrice(course.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
        }
        else if(title != null) {
            List<TravelCourse> travelCoursesByTitle = travelCourseRepository.findByTitle(title);
            return travelCoursesByTitle.stream()
                    .map(course -> TravelCourseResponse.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .summary(course.getSummary())
                        .publicContent(course.getPublicContent())
                        .placeCategory(course.getPlaceCategory())
                        .totalPrice(course.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
        }
        else if(category != null) {
            List<TravelCourse> travelCoursesByCategory = travelCourseRepository.findByPlaceCategory(category);
            return travelCoursesByCategory.stream()
                    .map(course -> TravelCourseResponse.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .summary(course.getSummary())
                        .publicContent(course.getPublicContent())
                        .placeCategory(course.getPlaceCategory())
                        .totalPrice(course.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
        }
        else {
            List<TravelCourse> allCourses = travelCourseRepository.findAll();
            return allCourses.stream()
                    .map(course -> TravelCourseResponse.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .summary(course.getSummary())
                        .publicContent(course.getPublicContent())
                        .placeCategory(course.getPlaceCategory())
                        .totalPrice(course.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
        }
    }
    @Transactional(readOnly = true)
    public TravelCourseResponse getTravelCourseById(Long id) {
        Optional<TravelCourse> travelCourseOptional = travelCourseRepository.findById(id);
        TravelCourse travelCourse = travelCourseOptional.orElseThrow(() -> new IllegalArgumentException("Travel course not found with id: " + id));

        return TravelCourseResponse.builder()
                .id(travelCourse.getId())
                .title(travelCourse.getTitle())
                .summary(travelCourse.getSummary())
                .publicContent(travelCourse.getPublicContent())
                .placeCategory(travelCourse.getPlaceCategory())
                .totalPrice(travelCourse.getTotalPrice())
                .build();
    }
    @Transactional
    public CourseResultResponse deleteTravelCourse(Long id) {
            travelCourseRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 코스입니다 id: " + id));
            travelCourseRepository.deleteById(id);
            return CourseResultResponse.success("삭제 성공", id);
        
    }
    @Transactional
    public CourseResultResponse updateTravelCourse(Long id, TravelCourseCreateRequest request) {
        TravelCourse travelCourse = travelCourseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 여행 코스입니다 id: " + id));
        travelCourse.updateFromRequest(request);
        travelCourseRepository.save(travelCourse);
        return CourseResultResponse.success("수정 성공", id);
    }
}