package com.foodiesfinds.recipe_service.dto.tag;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeTagDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeTagResponseDTO extends BaseRecipeTagDTO {
    private Long recipeTagId;
}
