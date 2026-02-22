package com.foodiesfinds.recipe_service.core.response;

import com.foodiesfinds.recipe_service.dto.core.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
