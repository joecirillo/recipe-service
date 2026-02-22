package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.DuplicateEntityException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientUpdateDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepUpdateDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagUpdateDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import com.foodiesfinds.recipe_service.mapper.RecipeIngredientMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeInstructionStepsMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeTagMapper;
import com.foodiesfinds.recipe_service.repository.RecipeRepository;
import com.foodiesfinds.recipe_service.service.RecipeService;
import jakarta.transaction.Transactional;
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
  private final UnitServiceImpl unitService;

  @Autowired
  private final RecipeMapper recipeMapper;

  @Autowired
  private final RecipeIngredientMapper recipeIngredientMapper;

  @Autowired
  private final RecipeTagMapper recipeTagMapper;

  @Autowired
  private final RecipeInstructionStepsMapper instructionStepMapper;

  @Transactional
  public RecipeResponseDTO save(RecipeSaveDTO recipeSaveDTO) {

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

    // For each cuisine in the recipe, check if a cuisine with the same name already exists
    recipe.setCuisine(cuisineService.resolveCuisine(recipe.getCuisine()));

    // For each tag in the recipe, check if a tag with the same name already exists
    recipe.getTags().forEach(rt -> rt.setTag(tagService.resolveTag(rt.getTag())));

    // For each ingredient in the recipe, check if an ingredient with the same name already exists
    recipe.getIngredients().forEach(ri -> {
      ri.setIngredient(ingredientService.resolveIngredient(ri.getIngredient()));
      ri.setUnit(unitService.resolveUnit(ri.getUnit()));
    });

    log.info("Saving new recipe: {}", recipeSaveDTO.getName());
    return recipeMapper.toDTO(recipeRepository.save(recipe));
  }

  @Override
  public Collection<RecipeResponseDTO> list(int limit) {
    log.info("Fetching the first {} recipes", limit);
    List<Recipe> recipes = recipeRepository.findAll(PageRequest.of(0, limit)).toList();
    List<RecipeResponseDTO> recipesDTO;
    recipesDTO = recipes.stream()
        .map(recipeMapper::toDTO)
        .toList();
    return recipesDTO.stream().toList();
  }

  @Override
  public RecipeResponseDTO get(Long id) {
    log.info("Fetching recipe by id: {}", id);
    Recipe recipeById = recipeRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    return recipeMapper.toDTO(recipeById);
  }

  @Override
  @Transactional
  public RecipeResponseDTO update(RecipeUpdateDTO dto, Long id) {
    Recipe recipe = recipeRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Recipe not found with id: " + id));

    recipeMapper.updateRecipeFromDTO(dto, recipe);

    if(dto.getCuisine() != null) {
      recipe.setCuisine(cuisineService.resolveCuisine(recipe.getCuisine()));
    }

    if (dto.getIngredients() != null) {
      updateIngredients(recipe, dto.getIngredients());
    }
    if (dto.getTags() != null) {
      updateTags(recipe, dto.getTags());
    }
    if (dto.getSteps() != null) {
      updateSteps(recipe, dto.getSteps());
    }

    return recipeMapper.toDTO(recipe);
  }

  private void updateIngredients(Recipe recipe, List<RecipeIngredientUpdateDTO> dtos) {
    recipe.getIngredients().clear();
    recipeRepository.flush();

    dtos.forEach(dto -> {
      RecipeIngredient ri = recipeIngredientMapper.toEntity(dto);

      ri.setIngredient(ingredientService.resolveIngredient(ri.getIngredient()));
      ri.setUnit(unitService.resolveUnit(ri.getUnit()));

      recipe.getIngredients().add(ri);
    });
  }

  private void updateSteps(Recipe recipe, List<InstructionStepUpdateDTO> dtos) {
    recipe.getSteps().clear();
    recipeRepository.flush();

    dtos.stream()
        .map(instructionStepMapper::toEntity)
        .forEach(step -> recipe.getSteps().add(step));
  }

  private void updateTags(Recipe recipe, List<RecipeTagUpdateDTO> dtos) {
    recipe.getTags().clear();
    recipeRepository.flush();

    dtos.stream()
        .map(recipeTagMapper::toEntity)
        .forEach(tag -> recipe.getTags().add(tag));
  }

  @Override
  public void delete(Long id) throws NotFoundException {
    log.info("Deleting recipe by id: {}", id);
    recipeRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    recipeRepository.deleteById(id);
  }

  @Override
  public List<RecipeResponseDTO> search(String query) {
    List<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(query);
    return recipes.stream()
        .peek(recipe -> log.info("Mapped recipe to DTO: {}", recipe.getName()))
        .map(recipeMapper::toDTO)
        .toList();
  }

}