package com.foodiesfinds.recipe_service.common.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.foodiesfinds.recipe_service.dto.Response;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Response> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(NOT_FOUND)
        .body(Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of())
            .message("Recipe not found")
            .status(NOT_FOUND)
            .statusCode(NOT_FOUND.value())
            .build()
    );
  }
}
