package com.foodiesfinds.recipe_service.core.response;

import com.foodiesfinds.recipe_service.dto.core.ErrorResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class ErrorResponseFactory {

  public ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
    String currentPath = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest().getRequestURI();

    return ResponseEntity.status(status)
        .body(ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(status)
            .message(message)
            .path(currentPath)
            .build()
        );
  }

}
