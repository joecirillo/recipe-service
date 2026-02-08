package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.ingredient.IngredientResponseDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {

  Ingredient toEntity(IngredientResponseDTO ingredientDTO);

  IngredientResponseDTO toDTO(Ingredient ingredient);

}
