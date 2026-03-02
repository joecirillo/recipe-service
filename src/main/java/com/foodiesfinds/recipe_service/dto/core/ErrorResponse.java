package com.foodiesfinds.recipe_service.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@AllArgsConstructor
@SuperBuilder
public class ErrorResponse {
    private Instant timestamp;
    private HttpStatus status;
    private String message;
    private String path;
}
