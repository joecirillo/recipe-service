package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.dto.InstructionStepDTO;
import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.dto.TagDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import com.foodiesfinds.recipe_service.entity.RecipeInstructionStep;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
    uses = {IngredientMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper {

  @Mapping(target = "ingredients", source = "ingredients")
  @Mapping(target = "steps", source = "steps")
  @Mapping(target = "tags", source = "tags")
  @Mapping(source = "cuisine.cuisineId", target = "cuisineId")
  @Mapping(source = "cuisine.cuisineName", target = "cuisineName")

  RecipeDTO toDTO(Recipe recipe);

  Recipe toEntity(RecipeDTO recipeDTO);

  @Mapping(source = "ingredient.ingredientId", target = "ingredientId")
  @Mapping(source = "ingredient.ingredientName", target = "ingredientName")
  @Mapping(source = "unit.unitId", target = "unitId")
  @Mapping(source = "unit.unitName", target = "unitName")
  @Mapping(source = "unit.abbreviation", target = "abbreviation")
  @Mapping(source = "recipeIngredientId", target = "recipeIngredientId")
  IngredientDTO map(RecipeIngredient recipeIngredient);

  InstructionStepDTO map(RecipeInstructionStep recipeInstructionStep);

  @Mapping(source = "id.recipeId", target = "recipeId")
  @Mapping(source = "tag.tagId", target = "tagId")
  @Mapping(source = "tag.tagName", target = "tagName")
  TagDTO map(RecipeTag recipeTag);

  List<InstructionStepDTO> mapInstructionSteps(List<RecipeInstructionStep> steps);

  List<TagDTO> mapRecipeTags(List<RecipeTag> tags);

}
