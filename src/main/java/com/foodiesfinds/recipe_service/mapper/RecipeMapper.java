package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
    RecipeIngredientMapper.class,
    RecipeInstructionStepsMapper.class,
    RecipeTagMapper.class
})
public interface RecipeMapper {

  Recipe toEntity(RecipeResponseDTO dto);

  Recipe toEntity(RecipeSaveDTO dto);

  RecipeResponseDTO toDTO(Recipe entity);

}