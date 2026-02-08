package com.foodiesfinds.recipe_service.dto.ingredient;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeIngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO extends BaseRecipeIngredientDTO {
  private Long recipeIngredientId;
}
