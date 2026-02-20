package com.foodiesfinds.recipe_service.dto.ingredient;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeIngredientDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientResponseDTO extends BaseRecipeIngredientDTO {
  private Long recipeIngredientId;
  private String unitName;
  private String abbreviation;
}
