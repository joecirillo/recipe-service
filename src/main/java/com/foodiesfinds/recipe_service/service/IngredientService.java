package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.ingredient.IngredientResponseDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;

public interface IngredientService {

    IngredientResponseDTO getIngredientById(Long ingredientId);

    Ingredient resolveIngredient(Ingredient ingredientDTO);

}
