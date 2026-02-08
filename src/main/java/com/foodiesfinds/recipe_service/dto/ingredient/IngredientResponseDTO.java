package com.foodiesfinds.recipe_service.dto.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientResponseDTO {

  private Long id;
  private String name;

}
