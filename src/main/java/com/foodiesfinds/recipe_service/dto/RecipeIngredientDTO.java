package com.foodiesfinds.recipe_service.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDTO {

  private Long recipeIngredientId;
  private Long id;
  private Long recipeId;
  private String name;
  private Long unitId;
  private String unitName;
  private String abbreviation;
  private BigDecimal quantity;
  private String notes;

}
