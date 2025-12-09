package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;

public interface IngredientService {

  IngredientDTO getIngredientById(Long ingredientId);

}
