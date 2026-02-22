package com.foodiesfinds.recipe_service.dto.recipe;

import com.foodiesfinds.recipe_service.dto.base.BaseRecipeDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientResponseDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepResponseDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDTO extends BaseRecipeDTO<
        RecipeIngredientResponseDTO,
        RecipeTagResponseDTO,
        InstructionStepResponseDTO> {
    private Long id;
    private String imageUrl;
    private LocalDate createdAt;
    private LocalDateTime updatedAt;
}
