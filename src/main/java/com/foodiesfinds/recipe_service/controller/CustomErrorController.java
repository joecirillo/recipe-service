package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.ErrorResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

  @Autowired
  private ErrorResponseFactory errorResponseFactory;

  @RequestMapping
  public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    Exception exception = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

    HttpStatus status = HttpStatus.resolve(statusCode);
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    // Provide better default messages
    if (message == null || message.isBlank()) {
      message = getDefaultMessage(status, exception);
    }

    return errorResponseFactory.buildErrorResponse(status, message);
  }

  private String getDefaultMessage(HttpStatus status, Exception exception) {
    return switch (status) {
      case BAD_REQUEST -> "Invalid request";
      case UNAUTHORIZED -> "Authentication required";
      case FORBIDDEN -> "Access denied";
      case NOT_FOUND -> "Resource not found";
      case METHOD_NOT_ALLOWED -> "HTTP method not allowed";
      case UNSUPPORTED_MEDIA_TYPE -> "Unsupported media type";
      case INTERNAL_SERVER_ERROR -> "Internal server error";
      default -> status.getReasonPhrase();
    };
  }
}
