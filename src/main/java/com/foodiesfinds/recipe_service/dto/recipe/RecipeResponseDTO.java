package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepDTO;
import com.foodiesfinds.recipe_service.dto.instruction.RecipeIngredientDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagDTO;
import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDTO extends BaseRecipeDTO<
    RecipeIngredientDTO,
    RecipeTagDTO,
    InstructionStepDTO
    > {
  private Long id;
  private String imageUrl;
  private LocalDate createdAt;
  private LocalDateTime updatedAt;

}
