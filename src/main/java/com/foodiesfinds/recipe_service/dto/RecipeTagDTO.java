package com.foodiesfinds.recipe_service.dto;

import com.foodiesfinds.recipe_service.entity.RecipeTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTagDTO extends RecipeTagSaveDTO {
  private Long recipeTagId;
}
