package com.foodiesfinds.recipe_service.dto;

import lombok.Data;

@Data
public class RecipeTagDTO {

  private Long recipeTagId;
  private Long tagId;
  String tagName;

}
