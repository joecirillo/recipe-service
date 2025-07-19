package com.foodiesfinds.recipe_service.service.implementation;
import com.foodiesfinds.recipe_service.model.Recipe;
import com.foodiesfinds.recipe_service.repository.RecipeRepository;
import com.foodiesfinds.recipe_service.service.RecipeService;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService {

  private final RecipeRepository recipeRepository;

  @Override
  public Recipe create(Recipe recipe) {
    log.info("Saving new recipe: {}", recipe.getRecipeName());
    return recipeRepository.save(recipe);
  }

  @Override
  public Collection<Recipe> list(int limit) {
    log.info("Fetching the first {} recipes", limit);
    return recipeRepository.findAll(PageRequest.of(0, limit)).toList();
  }

  @Override
  public Recipe get(Long id) {
    log.info("Fetching recipe by id: {}", id);
    return recipeRepository.findById(id).get();
  }

  @Override
  public Recipe update(Recipe recipe) {
    log.info("Updating recipe: {}", recipe.getRecipeName());
    return recipeRepository.save(recipe);
  }

  @Override
  public Boolean delete(Long id) {
    log.info("Deleting recipe by id: {}", id);
    recipeRepository.deleteById(id);
    return Boolean.TRUE;
  }

  @Override
  public List<Recipe> search(String query) {
    return recipeRepository.findByRecipeNameContainingIgnoreCase(query);
  }

}