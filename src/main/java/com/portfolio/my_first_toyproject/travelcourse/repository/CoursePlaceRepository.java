package com.portfolio.my_first_toyproject.travelcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.portfolio.my_first_toyproject.travelcourse.domain.CoursePlace;

public interface CoursePlaceRepository extends JpaRepository<CoursePlace, Long> {
    
}
