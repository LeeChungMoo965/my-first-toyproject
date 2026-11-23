package com.portfolio.my_first_toyproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.portfolio.my_first_toyproject.travelcourse.dto.CourseResultResponse;

@RestControllerAdvice
public class GloberExceptionHender {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CourseResultResponse> handleIllegalException(IllegalArgumentException e) {
        CourseResultResponse response = CourseResultResponse.fail(e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
