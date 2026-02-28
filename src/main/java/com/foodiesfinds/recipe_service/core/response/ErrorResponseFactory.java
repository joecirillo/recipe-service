package com.foodiesfinds.recipe_service.core.response;

import com.foodiesfinds.recipe_service.dto.core.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;


@Component
public class ErrorResponseFactory {

    public ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        String currentPath = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRequestURI();

        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(status)
                        .message(message)
                        .path(currentPath)
                        .build()
                );
    }

}
