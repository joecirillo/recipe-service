package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepSaveDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientSaveDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagSaveDTO;
import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeSaveDTO extends BaseRecipeDTO<
    RecipeIngredientSaveDTO,
    RecipeTagSaveDTO,
    InstructionStepSaveDTO
    > {

  @Override
  @NotNull(message = "Recipe must have at least one ingredient.")
  @NotEmpty(message = "Ingredients list cannot be empty.")
  public List<RecipeIngredientSaveDTO> getIngredients() {
    return super.getIngredients();
  }

  @Override
  @NotNull(message = "Recipe must have at least one step.")
  @NotEmpty(message = "Steps list cannot be empty.")
  public List<InstructionStepSaveDTO> getSteps() {
    return super.getSteps();
  }

}
