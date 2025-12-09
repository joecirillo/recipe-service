package com.foodiesfinds.recipe_service.dto;

import lombok.Data;

@Data
public class InstructionStepDTO {

  private Long stepId;
  private Short stepNumber;
  private String description;
  private String tip;

}
