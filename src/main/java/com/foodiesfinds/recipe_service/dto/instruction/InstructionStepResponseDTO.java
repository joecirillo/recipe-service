package com.foodiesfinds.recipe_service.dto.instruction;

import com.foodiesfinds.recipe_service.dto.base.BaseInstructionStepDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructionStepResponseDTO extends BaseInstructionStepDTO {
    private Long stepId;
}
