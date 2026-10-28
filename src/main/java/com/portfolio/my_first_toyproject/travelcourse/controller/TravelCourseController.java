package com.portfolio.my_first_toyproject.travelcourse.controller;

import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseCreateRequest;
import com.portfolio.my_first_toyproject.travelcourse.service.TravelCourseService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses") // 공통 URL 세팅
@RequiredArgsConstructor
public class TravelCourseController {

    private final TravelCourseService travelCourseService;

    // POST 요청을 매핑 (@PostMapping)
    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody TravelCourseCreateRequest request) {
        
        // 포스트맨에서 날아온 JSON 데이터가 DTO(request)에 예쁘게 담겨서 들어옵니다.
        // 우리는 그걸 그대로 서비스(두뇌)로 토스!
        travelCourseService.createTravelCourse(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}