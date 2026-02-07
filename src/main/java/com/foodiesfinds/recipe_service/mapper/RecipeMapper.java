package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.dto.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;
import com.foodiesfinds.recipe_service.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
    RecipeIngredientMapper.class,
    RecipeInstructionStepsMapper.class,
    RecipeTagMapper.class
})
public interface RecipeMapper {

  Recipe toEntity(RecipeDTO dto);

  Recipe toEntity(RecipeSaveDTO dto);

  RecipeDTO toDTO(Recipe entity);

}