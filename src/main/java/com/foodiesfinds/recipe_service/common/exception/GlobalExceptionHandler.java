package com.foodiesfinds.recipe_service.common.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.foodiesfinds.recipe_service.dto.core.ErrorResponse;
import com.foodiesfinds.recipe_service.dto.core.Response;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        ex.getMessage(),
        request.getRequestURI()
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Response> handleNotFoundException(NotFoundException ex) {
    log.info("NotFoundException: {}", ex.getMessage());
    return buildResponse(NOT_FOUND, ex.getMessage());
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
