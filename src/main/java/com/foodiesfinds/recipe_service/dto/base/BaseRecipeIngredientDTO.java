package com.foodiesfinds.recipe_service.dto.base;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRecipeIngredientDTO {
  private Long id;
  private String name;
  private Long unitId;
  private String unitName;
  private String abbreviation;
  private BigDecimal quantity;
  private String notes;

}
