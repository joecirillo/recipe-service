package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.tag.RecipeTagResponseDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagSaveDTO;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeTagMapper {

  @Mapping(target = "tag.id", source = "id")
  @Mapping(target = "tag.name", source = "name")
  @Mapping(target = "recipeTagId", source = "recipeTagId")
  RecipeTag toEntity(RecipeTagResponseDTO dto);

  @Mapping(target = "tag.id", source = "id")
  @Mapping(target = "tag.name", source = "name")
  RecipeTag toEntity(RecipeTagSaveDTO dto);

  @Mapping(target = "id", source = "tag.id")
  @Mapping(target = "name", source = "tag.name")
  @Mapping(target = "recipeTagId", source = "recipeTagId")
  RecipeTagResponseDTO toDTO(RecipeTag entity);

}
