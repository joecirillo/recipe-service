package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.dto.InstructionStepDTO;
import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.dto.TagDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import com.foodiesfinds.recipe_service.entity.RecipeInstructionStep;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface RecipeMapper {

  @Mapping(source = "recipeName", target = "recipeName")
  RecipeDTO toRecipeDTO(Recipe recipe);

  @Mapping(source = "ingredient.ingredientId", target = "ingredientId")
  @Mapping(source = "ingredient.ingredientName", target = "ingredientName")
  @Mapping(source = "unit.unitName", target = "unitName")
  @Mapping(source = "unit.unitId", target = "unitId")
  @Mapping(source = "unit.abbreviation", target = "abbreviation")
  IngredientDTO toIngredientDTO(RecipeIngredient recipeIngredient);

  InstructionStepDTO toInstructionStepDTO(RecipeInstructionStep recipeInstructionStep);

  @Mapping(source = "id.recipeId", target = "recipeId")
  @Mapping(source = "tag.tagId", target = "tagId")
  @Mapping(source = "tag.tagName", target = "tagName")
  TagDTO toTagDTO(RecipeTag recipeTag);

}
