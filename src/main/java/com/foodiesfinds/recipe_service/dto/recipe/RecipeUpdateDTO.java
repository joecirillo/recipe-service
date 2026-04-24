package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientUpdateDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepUpdateDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagUpdateDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeUpdateDTO extends BaseRecipeDTO<
        RecipeIngredientUpdateDTO,
        RecipeTagUpdateDTO,
        InstructionStepUpdateDTO> {

    @Override
    @NotEmpty(message = "There must be at least one ingredient.")
    public List<RecipeIngredientUpdateDTO> getIngredients() {
        return super.getIngredients();
    }

    @Override
    @NotEmpty(message = "There must be at least one step.")
    public List<InstructionStepUpdateDTO> getSteps() {
        return super.getSteps();
    }
}
