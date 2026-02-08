package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepSaveDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientSaveDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagSaveDTO;
import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeSaveDTO extends BaseRecipeDTO<
    RecipeIngredientSaveDTO,
    RecipeTagSaveDTO,
    InstructionStepSaveDTO
    > {

}
