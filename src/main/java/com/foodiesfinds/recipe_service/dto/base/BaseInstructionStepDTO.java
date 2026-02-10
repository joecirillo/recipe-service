package com.foodiesfinds.recipe_service.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseInstructionStepDTO {
  private Short stepNumber;
  private String description;
  private String tip;
}
