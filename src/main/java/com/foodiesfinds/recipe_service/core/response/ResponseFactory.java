package com.foodiesfinds.recipe_service.core.response;

import com.foodiesfinds.recipe_service.dto.core.Response;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class ResponseFactory {

  public ResponseEntity<Response> buildResponse(HttpStatus status, String message, Object data) {
    return ResponseEntity.status(status)
        .body(Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(data)
            .message(message)
            .status(status)
            .build()
        );
  }

}
