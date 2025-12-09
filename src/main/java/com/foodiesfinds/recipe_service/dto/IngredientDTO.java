package com.foodiesfinds.recipe_service.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class IngredientDTO {

  private Long recipeIngredientId;
  private Long ingredientId;
  private String ingredientName;
  private Long unitId;
  private String unitName;
  private String abbreviation;
  private BigDecimal quantity;
  private String notes;

}
