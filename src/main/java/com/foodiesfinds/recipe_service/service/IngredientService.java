package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.IngredientResponseDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;

import java.util.List;

public interface IngredientService {

    IngredientResponseDTO getIngredientById(Long ingredientId);

    Ingredient resolveIngredient(Ingredient ingredientDTO);

    List<IngredientResponseDTO> list();

    List<NamedEntityDTO> search(String query);

}
