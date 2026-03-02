package com.foodiesfinds.recipe_service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class Response {
    protected Instant timeStamp;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String path;
    protected String developerMessage;
    protected Object data;

}
