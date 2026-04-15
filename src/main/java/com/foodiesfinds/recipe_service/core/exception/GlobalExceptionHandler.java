package com.foodiesfinds.recipe_service.core.exception;

import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        String message = fieldError.getDefaultMessage();
        return errorResponseFactory.buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return errorResponseFactory.buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return errorResponseFactory.buildErrorResponse(NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException ex) {
        return errorResponseFactory.buildErrorResponse(CONFLICT, ex.getMessage());
    }
}
