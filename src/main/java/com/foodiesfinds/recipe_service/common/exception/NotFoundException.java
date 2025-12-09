package com.foodiesfinds.recipe_service.common.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("Recipe not found");
  }

  public NotFoundException(String message) {
    super(message);
  }


}
