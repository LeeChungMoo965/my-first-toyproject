package com.portfolio.my_first_toyproject.travelcourse.controller;

import com.portfolio.my_first_toyproject.travelcourse.domain.PlaceCategory;
import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseCreateRequest;
import com.portfolio.my_first_toyproject.travelcourse.dto.TravelCourseResponse;
import com.portfolio.my_first_toyproject.travelcourse.repository.TravelCourseRepository;
import com.portfolio.my_first_toyproject.travelcourse.service.TravelCourseService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/courses") // 공통 URL 세팅
@RequiredArgsConstructor
public class TravelCourseController {

    private final TravelCourseRepository travelCourseRepository;
    private final TravelCourseService travelCourseService;

    

    // POST 요청을 매핑 (@PostMapping)
    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody TravelCourseCreateRequest request) {
        
        // 포스트맨에서 날아온 JSON 데이터가 DTO(request)에 예쁘게 담겨서 들어옵니다.
        // 우리는 그걸 그대로 서비스(두뇌)로 토스!
        travelCourseService.createTravelCourse(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
        @GetMapping
        public ResponseEntity<List<TravelCourseResponse>> getCourse(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) PlaceCategory category
        ) { 
            // 서비스 계층에 요청을 전달하고 결과를 받아옵니다.
            List<TravelCourseResponse> courses = travelCourseService.getTravelCourse(title, category);
            
            // 결과를 ResponseEntity로 감싸서 반환합니다.
            return ResponseEntity.ok(courses);
        }
        @GetMapping("/{id}")
        public ResponseEntity<TravelCourseResponse> getCourseById(@PathVariable Long id){
            
            TravelCourseResponse course = travelCourseService.getTravelCourseById(id);
            return ResponseEntity.ok(course);
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
            travelCourseService.deleteTravelCourse(id);
            return ResponseEntity.noContent().build();
        }
        @PutMapping("/{id}")
        public ResponseEntity<CourseResultResponse> updateCourse(@PathVariable Long id, @RequestBody TravelCourseCreateRequest request){
            
            CourseResultResponse response = travelCourseService.updateTravelCourse(id, request);
            return ResponseEntity.ok(response);
        }
}