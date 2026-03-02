package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientSaveDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepSaveDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagSaveDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RecipeSaveDTO extends BaseRecipeDTO<
        RecipeIngredientSaveDTO,
        RecipeTagSaveDTO,
        InstructionStepSaveDTO
        > {

    @Override
    @NotNull
    @Positive(message = "Calories must be a positive value.")
    public Short getCalories() {
        return super.getCalories();
    }

    @Override
    @NotNull
    @Positive(message = "Servings must be a more than zero.")
    public short getServings() {
        return super.getServings();
    }

    @Override
    @NotNull
    @Min(value = 0, message = "Cooking time cannot be negative.")
    public short getCookingTime() {
        return super.getCookingTime();
    }

    @Override
    @NotNull
    @Positive(message = "Preparation time must be a more than zero.")
    public short getPreparationTime() {
        return super.getPreparationTime();
    }

    @Override
    @NotNull(message = "Recipe must have at least one ingredient.")
    @NotEmpty(message = "Ingredients list cannot be empty.")
    public List<RecipeIngredientSaveDTO> getIngredients() {
        return super.getIngredients();
    }

    @Override
    @NotNull(message = "Recipe must have at least one step.")
    @NotEmpty(message = "Steps list cannot be empty.")
    public List<InstructionStepSaveDTO> getSteps() {
        return super.getSteps();
    }

    @Override
    @NotNull(message = "Recipe name is required.")
    public String getName() {
        return super.getName();
    }

}
