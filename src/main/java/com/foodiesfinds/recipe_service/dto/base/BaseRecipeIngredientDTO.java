package com.foodiesfinds.recipe_service.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRecipeIngredientDTO {
    private Long id;
    private String name;
    private Long unitId;
    private BigDecimal quantity;
    private String notes;

}
