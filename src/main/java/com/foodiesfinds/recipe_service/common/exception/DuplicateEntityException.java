package com.foodiesfinds.recipe_service.common.exception;

public class DataIntegrityViolationException extends RuntimeException {

  public DataIntegrityViolationException(String message) {
    super(message);
  }

  public DataIntegrityViolationException() {
    super("Data integrity violation");
  }

}
