package com.foodiesfinds.recipe_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO extends RecipeIngredientSaveDTO {
  private Long recipeIngredientId;
}
