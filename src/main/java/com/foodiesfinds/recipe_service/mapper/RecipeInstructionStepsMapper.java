package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepResponseDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepUpdateDTO;
import com.foodiesfinds.recipe_service.entity.RecipeInstructionStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeInstructionStepsMapper {

  @Mapping(target = "description", source = "description")
  RecipeInstructionStep toEntity(String description);

  InstructionStepResponseDTO toDTO(RecipeInstructionStep entity);

  RecipeInstructionStep toEntity(InstructionStepUpdateDTO dto);

  InstructionStepUpdateDTO toUpdateDTO(RecipeInstructionStep entity);

}
