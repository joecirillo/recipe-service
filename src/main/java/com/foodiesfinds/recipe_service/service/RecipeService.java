package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import java.util.Collection;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface RecipeService {

  RecipeDTO save(RecipeDTO recipe);

  Collection<RecipeDTO> list(int limit);

  RecipeDTO get(Long id);

  RecipeDTO update(RecipeDTO recipe);

  void delete(Long id) throws NotFoundException;

  List<RecipeDTO> search(String query);

}