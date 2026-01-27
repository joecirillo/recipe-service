package com.foodiesfinds.recipe_service.service.implementation;
import com.foodiesfinds.recipe_service.common.exception.DuplicateEntityException;
import com.foodiesfinds.recipe_service.common.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.dto.RecipeIngredientDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.mapper.RecipeMapper;
import com.foodiesfinds.recipe_service.repository.IngredientRepository;
import com.foodiesfinds.recipe_service.repository.RecipeRepository;
import com.foodiesfinds.recipe_service.repository.TagRepository;
import com.foodiesfinds.recipe_service.service.RecipeService;
import com.foodiesfinds.recipe_service.service.TagService;
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

  private final IngredientRepository ingredientRepository;

  private final TagRepository tagRepository;

  @Autowired
  private final IngredientServiceImpl ingredientService;

  @Autowired
  private final TagService tagService;

  @Autowired
  private final RecipeMapper recipeMapper;

  @Transactional
  public RecipeDTO save(RecipeDTO recipeDTO) {
    if (recipeDTO.getId() != null) {
      throw new IllegalArgumentException("Recipe ID must be null for new recipes.");
    }

    // We don't want multiple tags with the same name
    long uniqueTagCount = recipeDTO.getTags().stream()
        .map(tag -> tag.getTagName().toLowerCase().trim())
        .distinct()
        .count();

    if (uniqueTagCount < recipeDTO.getTags().size()) {
      throw new DuplicateEntityException("The same tag cannot be added twice to a single recipe.");
    }

    recipeDTO.getTags().stream()
        .filter(tagDTO -> tagRepository.existsByTagName(tagDTO.getTagName()))
        .findFirst()
        .ifPresent(tagDTO -> {
          throw new DuplicateEntityException("Tag already exists: " + tagDTO.getTagName());
        });

    // The same ingredient used multiple times in a recipe is allowed since they may be used
    // in different quantities or forms (e.g., chopped, diced, etc.). However, we do want to check
    // if the ingredient already exists in the database to avoid duplicates across recipes
    recipeDTO.getIngredients().stream()
        .filter(ingredientDTO -> ingredientRepository.existsByName(ingredientDTO.getName()))
        .findFirst()
        .ifPresent(ingredientDTO -> {
          throw new DuplicateEntityException("Ingredient already exists: " + ingredientDTO.getName());
        });

    // The ingredient ID and name cannot both be provided because this will cause ambiguity
    // in determining which ingredient to associate with the recipe. The Ingredient entity is
    // currently set to prohibit name updates, but this is an extra check to ensure data integrity
    for (RecipeIngredientDTO ingredientDTO : recipeDTO.getIngredients()) {
      if (ingredientDTO.getId() != null && ingredientDTO.getName() != null) {
        throw new IllegalArgumentException(
            "Ingredient ID and name cannot both be provided for ingredient: "
                + ingredientDTO.getName());
      }
    }

    log.info("Saving new recipe: {}", recipeDTO.getName());
    return recipeMapper.toDTO(recipeRepository.save(recipeMapper.toEntity(recipeDTO)));
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
  }

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