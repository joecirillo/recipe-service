package com.foodiesfinds.recipe_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTagSaveDTO {
  private Long id;
  String name;
}
