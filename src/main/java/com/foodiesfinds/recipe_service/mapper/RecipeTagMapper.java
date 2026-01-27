package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.RecipeTagDTO;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeTagMapper {

  @Mapping(target = "tag.tagId", source = "tagId")
  @Mapping(target = "tag.tagName", source = "tagName")
  @Mapping(target = "recipeTagId", source = "recipeTagId")
  RecipeTag toEntity(RecipeTagDTO dto);

  @Mapping(target = "tagId", source = "tag.tagId")
  @Mapping(target = "tagName", source = "tag.tagName")
  @Mapping(target = "recipeTagId", source = "recipeTagId")
  RecipeTagDTO toDTO(RecipeTag entity);

}
