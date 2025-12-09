package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IngredientMapper {

  IngredientDTO toDTO(Ingredient ingredient);

  Ingredient toEntity(IngredientDTO ingredientDTO);

}
