package com.foodiesfinds.recipe_service.dto;

import com.foodiesfinds.recipe_service.entity.RecipeTagId;
import lombok.Data;

@Data
public class TagDTO {

  Long recipeId;
  Long tagId;
  String tagName;

}
