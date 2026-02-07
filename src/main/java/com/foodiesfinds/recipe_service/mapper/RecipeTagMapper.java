package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.RecipeTagDTO;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeTagMapper {

  @Mapping(target = "tag.id", source = "id")
  @Mapping(target = "tag.name", source = "name")
  @Mapping(target = "recipeTagId", source = "recipeTagId")
  RecipeTag toEntity(RecipeTagDTO dto);

  @Mapping(target = "id", source = "tag.id")
  @Mapping(target = "name", source = "tag.name")
  @Mapping(target = "recipeTagId", source = "recipeTagId")
  RecipeTagDTO toDTO(RecipeTag entity);

}
