package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.RecipeSearchParams;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientUpdateDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepUpdateDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.dto.tag.RecipeTagUpdateDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import com.foodiesfinds.recipe_service.mapper.RecipeIngredientMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeInstructionStepsMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeTagMapper;
import com.foodiesfinds.recipe_service.repository.RecipeRepository;
import com.foodiesfinds.recipe_service.service.CuisineService;
import com.foodiesfinds.recipe_service.specification.RecipeSpecification;
import com.foodiesfinds.recipe_service.service.IngredientService;
import com.foodiesfinds.recipe_service.service.RecipeService;
import com.foodiesfinds.recipe_service.service.TagService;
import com.foodiesfinds.recipe_service.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final CuisineService cuisineService;
    private final TagService tagService;
    private final UnitService unitService;
    private final RecipeMapper recipeMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final RecipeTagMapper recipeTagMapper;
    private final RecipeInstructionStepsMapper instructionStepMapper;

    @Override
    @Transactional
    public RecipeResponseDTO save(RecipeSaveDTO recipeSaveDTO) {

        Recipe recipe = recipeMapper.toEntity(recipeSaveDTO);

        // For each cuisine in the recipe, check if a cuisine with the same name already exists
        recipe.setCuisine(cuisineService.resolveCuisine(recipe.getCuisine()));

        List<RecipeTag> uniqueRecipeTags = recipe.getTags().stream()
                .peek(rt -> rt.setTag(tagService.resolveTag(rt.getTag())))
                .collect(Collectors.toMap(
                        rt -> rt.getTag().getName().toLowerCase().trim(),
                        rt -> rt,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();

        // Replace the list
        recipe.getTags().clear();
        recipe.getTags().addAll(uniqueRecipeTags);

        // For each ingredient in the recipe, check if an ingredient with the same name already exists
        recipe.getIngredients().forEach(ri -> {
            ri.setIngredient(ingredientService.resolveIngredient(ri.getIngredient()));
            ri.setUnit(unitService.resolveUnit(ri.getUnit()));
        });

        log.info("Saving new recipe: {}", recipeSaveDTO.getName());
        return recipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NamedEntityDTO> list(int limit) {
        log.info("Fetching the first {} recipes", limit);
        List<Recipe> recipes = recipeRepository.findAll(PageRequest.of(0, limit)).toList();
        return recipes.stream()
                .map(recipe -> new NamedEntityDTO(recipe.getId(), recipe.getName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
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

        if (dto.getCuisine() != null) {
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

        recipeRepository.flush();

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

        dtos.forEach(dto -> {
            RecipeTag rt = recipeTagMapper.toEntity(dto);
            rt.setTag(tagService.resolveTag(rt.getTag()));

            boolean alreadyExists = recipe.getTags().stream()
                    .anyMatch(existingRt -> existingRt.getTag().getId().equals(rt.getTag().getId()));

            if (!alreadyExists) {
                recipe.getTags().add(rt);
            }
        });
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting recipe by id: {}", id);
        recipeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NamedEntityDTO> search(RecipeSearchParams params) {
        return recipeRepository.findAll(RecipeSpecification.buildFrom(params)).stream()
                .map(recipe -> new NamedEntityDTO(recipe.getId(), recipe.getName()))
                .toList();
    }

}
