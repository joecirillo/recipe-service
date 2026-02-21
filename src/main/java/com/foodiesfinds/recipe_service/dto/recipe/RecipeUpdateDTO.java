package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientUpdateDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepSaveDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepUpdateDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagUpdateDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeUpdateDTO extends BaseRecipeDTO<
    RecipeIngredientUpdateDTO,
    RecipeTagUpdateDTO,
    InstructionStepUpdateDTO
    > {
  @NotNull(message = "Recipe ID cannot be null for update")
  private Long id;
  private String imageUrl;

}
