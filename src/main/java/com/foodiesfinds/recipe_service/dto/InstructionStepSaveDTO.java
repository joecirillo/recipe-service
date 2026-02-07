package com.foodiesfinds.recipe_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionStepSaveDTO {
  private Short stepNumber;
  private String description;
  private String tip;
}
