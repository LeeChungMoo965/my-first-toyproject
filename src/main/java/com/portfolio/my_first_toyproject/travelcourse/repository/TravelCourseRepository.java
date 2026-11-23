package com.portfolio.my_first_toyproject.travelcourse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.my_first_toyproject.travelcourse.domain.PlaceCategory;
import com.portfolio.my_first_toyproject.travelcourse.domain.TravelCourse;
import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseResponse;

public interface TravelCourseRepository extends JpaRepository<TravelCourse, Long> {
    List<TravelCourse> findByTitle(String title);
    List<TravelCourse> findByPlaceCategory(PlaceCategory category);
    List<TravelCourse> findByTitleAndPlaceCategory(String title, PlaceCategory category);
    Optional<TravelCourse> findById(Long id);
    void deleteById(Long id);
    TravelCourse save(TravelCourse travelCourse);
}
