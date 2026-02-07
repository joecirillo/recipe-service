package com.foodiesfinds.recipe_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionStepDTO extends InstructionStepSaveDTO {
  private Long stepId;
}
