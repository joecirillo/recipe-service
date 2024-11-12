package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.model.Recipe;
import java.util.Collection;

public interface RecipeService {

  Recipe create(Recipe recipe);

  Collection<Recipe> list(int limit);

  Recipe get(Long id);

  Recipe update(Recipe recipe);

  Boolean delete(Long id);

  Collection<Recipe> search(String query);

}