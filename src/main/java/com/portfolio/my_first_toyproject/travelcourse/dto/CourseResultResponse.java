package com.portfolio.my_first_toyproject.travelcourse.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseResultResponse {
    private String courseCode; // 예: "SUCCESS", "FAIL" (또는 COURSE_DELETED 같은 코드)
    private String message;    // 예: "삭제 성공", "해당 코스가 없습니다."
    private Long courseId;

    public static CourseResultResponse success(String message, Long courseId) {
        return CourseResultResponse.builder()
                .courseCode("SUCCESS")
                .message(message)
                .courseId(courseId)
                .build();
    }

    public static CourseResultResponse fail(String message, Long courseId) {
        return CourseResultResponse.builder()
                .courseCode("FAIL")
                .message(message)
                .courseId(courseId)
                .build();
    }
}