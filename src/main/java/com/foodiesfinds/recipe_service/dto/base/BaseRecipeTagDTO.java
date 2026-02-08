package com.foodiesfinds.recipe_service.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRecipeTagDTO {
  private Long id;
  String name;

}
