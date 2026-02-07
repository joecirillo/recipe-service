package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;

public interface IngredientService {

  IngredientDTO getIngredientById(Long ingredientId);

  Ingredient resolveIngredient(Ingredient ingredientDTO);

}
