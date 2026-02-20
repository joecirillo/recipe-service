package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import java.util.Collection;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface RecipeService {

  RecipeResponseDTO save(RecipeSaveDTO recipeDTO);

  Collection<RecipeResponseDTO> list(int limit);

  RecipeResponseDTO get(Long id);

  RecipeResponseDTO update(RecipeUpdateDTO recipe, Long id);

  void delete(Long id) throws NotFoundException;

  List<RecipeResponseDTO> search(String query);

}