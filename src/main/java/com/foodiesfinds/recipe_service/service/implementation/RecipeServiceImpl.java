package com.foodiesfinds.recipe_service.service.implementation;
import com.foodiesfinds.recipe_service.common.exception.DuplicateEntityException;
import com.foodiesfinds.recipe_service.common.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.dto.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.mapper.RecipeMapper;
import com.foodiesfinds.recipe_service.repository.RecipeRepository;
import com.foodiesfinds.recipe_service.service.RecipeService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService {


  private final RecipeRepository recipeRepository;

  @Autowired
  private final IngredientServiceImpl ingredientService;

  @Autowired
  private final CuisineServiceIml cuisineService;

  @Autowired
  private final TagServiceImpl tagService;

  @Autowired
  private final RecipeMapper recipeMapper;

  @Transactional
  public RecipeDTO save(RecipeSaveDTO recipeSaveDTO) {

    // We don't want multiple tags with the same name
    long uniqueTagCount = recipeSaveDTO.getTags().stream()
        .map(tag -> tag.getName().toLowerCase().trim())
        .distinct()
        .count();

    if (uniqueTagCount < recipeSaveDTO.getTags().size()) {
      throw new DuplicateEntityException("The same tag cannot be added more than once"
          + "to a single recipe.");
    }

    Recipe recipe = recipeMapper.toEntity(recipeSaveDTO);

    cuisineService.resolveCuisine(recipe.getCuisine());

    // For each tag in the recipe, check if a tag with the same name already exists in the database
    recipe.getTags().forEach(rt -> rt.setTag(tagService.resolveTag(rt.getTag())));

    // For each ingredient in the recipe, check if an ingredient with the same name already
    // exists in the database
    recipe.getIngredients().forEach(ri ->
        ri.setIngredient(ingredientService.resolveIngredient(ri.getIngredient()))
    );

    log.info("Saving new recipe: {}", recipeSaveDTO.getName());
    return recipeMapper.toDTO(recipeRepository.save(recipe));
  }

  @Override
  public Collection<RecipeDTO> list(int limit) {
    log.info("Fetching the first {} recipes", limit);
    List<Recipe> recipes = recipeRepository.findAll(PageRequest.of(0, limit)).toList();
    List<RecipeDTO> recipesDTO = new ArrayList<>();
    recipesDTO = recipes.stream()
        .map(recipeMapper::toDTO)
        .toList();
    return recipesDTO.stream().toList();
  }≠

  @Override
  public RecipeDTO get(Long id) {
    log.info("Fetching recipe by id: {}", id);
    Recipe recipeById = recipeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    return recipeMapper.toDTO(recipeById);
  }

  @Override
  public RecipeDTO update(RecipeDTO recipe) {
    log.info("Updating recipe: {}", recipe.getName());
    return recipeMapper.toDTO(recipeRepository.save(recipeMapper.toEntity(recipe)));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    log.info("Deleting recipe by id: {}", id);
    recipeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException());
    recipeRepository.deleteById(id);
  }

  @Override
  public List<RecipeDTO> search(String query) {
    List<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(query);
    return recipes.stream()
        .peek(recipe -> log.info("Mapped recipe to DTO: {}", recipe.getName()))
        .map(recipeMapper::toDTO)
        .toList();
  }

}