package com.foodiesfinds.recipe_service.common.exception;

public class BadRequestException extends RuntimeException {

  public BadRequestException() {
    super("Bad request");
  }

  public BadRequestException(String message) {
    super(message);
  }
}
