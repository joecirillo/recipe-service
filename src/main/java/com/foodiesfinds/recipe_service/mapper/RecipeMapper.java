package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {
    RecipeIngredientMapper.class,
    RecipeInstructionStepsMapper.class,
    RecipeTagMapper.class
})
public interface RecipeMapper {

  Recipe toEntity(RecipeResponseDTO dto);

  Recipe toEntity(RecipeSaveDTO dto);

  @Mapping(target = "ingredients", ignore = true)
  @Mapping(target = "steps", ignore = true)
  @Mapping(target = "tags", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Recipe toEntity(RecipeUpdateDTO dto, @MappingTarget Recipe recipe);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "ingredients", ignore = true)
  @Mapping(target = "steps", ignore = true)
  @Mapping(target = "tags", ignore = true)
  @Mapping(target = "cuisine", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateRecipeFromDTO(RecipeUpdateDTO dto, @MappingTarget Recipe recipe);

  RecipeUpdateDTO toUpdateDTO(Recipe entity);

  RecipeResponseDTO toDTO(Recipe entity);

}