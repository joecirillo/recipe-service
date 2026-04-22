package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.RecipeSearchParams;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;

import java.util.List;

public interface RecipeService {

    RecipeResponseDTO save(RecipeSaveDTO recipeDTO);

    List<NamedEntityDTO> list(int limit);

    RecipeResponseDTO get(Long id);

    RecipeResponseDTO update(RecipeUpdateDTO recipe, Long id);

    void delete(Long id) throws NotFoundException;

    List<NamedEntityDTO> search(RecipeSearchParams params);

}
