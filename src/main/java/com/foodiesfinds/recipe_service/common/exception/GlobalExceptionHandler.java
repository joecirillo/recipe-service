package com.foodiesfinds.recipe_service.common.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.foodiesfinds.recipe_service.dto.Response;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Response> handleNotFoundException(NotFoundException ex) {
    return buildResponse(NOT_FOUND, "Recipe not found");
  }

  @ExceptionHandler(DuplicateEntityException.class)
  public ResponseEntity<Response> handleDuplicateEntityException(DuplicateEntityException ex) {
    return buildResponse(CONFLICT, ex.getMessage());
  }

  private ResponseEntity<Response> buildResponse(HttpStatus status, String message) {
    return ResponseEntity.status(status)
        .body(Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of())
            .message(message)
            .status(status)
            .statusCode(status.value())
            .build()
    );
  }
}
