package com.foodiesfinds.recipe_service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class Response {
  protected LocalDateTime timeStamp;
  protected HttpStatus status;
  protected String reason;
  protected String message;
  protected String path;
  protected String developerMessage;
  protected Object data;

}
