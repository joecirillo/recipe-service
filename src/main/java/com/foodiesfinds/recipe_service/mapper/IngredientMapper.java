package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {

  Ingredient toEntity(IngredientDTO ingredientDTO);

  IngredientDTO toDTO(Ingredient ingredient);

}
