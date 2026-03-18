package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import com.foodiesfinds.recipe_service.entity.Tag;
import com.foodiesfinds.recipe_service.entity.Unit;
import com.foodiesfinds.recipe_service.mapper.RecipeIngredientMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeInstructionStepsMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeMapper;
import com.foodiesfinds.recipe_service.mapper.RecipeTagMapper;
import com.foodiesfinds.recipe_service.repository.RecipeRepository;
import com.foodiesfinds.recipe_service.service.implementation.CuisineServiceIml;
import com.foodiesfinds.recipe_service.service.implementation.IngredientServiceImpl;
import com.foodiesfinds.recipe_service.service.implementation.RecipeServiceImpl;
import com.foodiesfinds.recipe_service.service.implementation.TagServiceImpl;
import com.foodiesfinds.recipe_service.service.implementation.UnitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock RecipeRepository recipeRepository;
    @Mock IngredientServiceImpl ingredientService;
    @Mock CuisineServiceIml cuisineService;
    @Mock TagServiceImpl tagService;
    @Mock UnitServiceImpl unitService;
    @Mock RecipeMapper recipeMapper;
    @Mock RecipeIngredientMapper recipeIngredientMapper;
    @Mock RecipeTagMapper recipeTagMapper;
    @Mock RecipeInstructionStepsMapper instructionStepMapper;

    @InjectMocks
    RecipeServiceImpl recipeService;

    private Recipe buildRecipeWithDependencies() {
        Cuisine cuisine = new Cuisine(null, "Italian");

        Tag tag = new Tag(null, "dinner");
        RecipeTag recipeTag = new RecipeTag();
        recipeTag.setTag(tag);

        Ingredient ingredient = new Ingredient(null, "pasta");
        Unit unit = new Unit(1L, "cups", "c");
        RecipeIngredient ri = new RecipeIngredient();
        ri.setIngredient(ingredient);
        ri.setUnit(unit);

        Recipe recipe = new Recipe();
        recipe.setCuisine(cuisine);
        recipe.getTags().add(recipeTag);
        recipe.getIngredients().add(ri);
        return recipe;
    }

    private RecipeResponseDTO buildResponseDTO() {
        RecipeResponseDTO dto = new RecipeResponseDTO();
        dto.setId(1L);
        dto.setName("Pasta");
        return dto;
    }

    // --- save ---

    @Test
    void save_success() {
        Recipe recipe = buildRecipeWithDependencies();
        Cuisine resolvedCuisine = new Cuisine((short) 1, "Italian");
        Tag resolvedTag = new Tag(10L, "dinner");
        Ingredient resolvedIngredient = new Ingredient(5L, "pasta");
        Unit resolvedUnit = new Unit(1L, "cups", "c");
        RecipeResponseDTO responseDTO = buildResponseDTO();

        when(recipeMapper.toEntity(any(RecipeSaveDTO.class))).thenReturn(recipe);
        when(cuisineService.resolveCuisine(recipe.getCuisine())).thenReturn(resolvedCuisine);
        when(tagService.resolveTag(any(Tag.class))).thenReturn(resolvedTag);
        when(ingredientService.resolveIngredient(any(Ingredient.class))).thenReturn(resolvedIngredient);
        when(unitService.resolveUnit(any(Unit.class))).thenReturn(resolvedUnit);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.toDTO(recipe)).thenReturn(responseDTO);

        RecipeResponseDTO result = recipeService.save(new RecipeSaveDTO());

        assertThat(result.getId()).isEqualTo(1L);
        verify(recipeRepository).save(recipe);
        verify(cuisineService).resolveCuisine(any());
        verify(tagService).resolveTag(any());
        verify(ingredientService).resolveIngredient(any());
        verify(unitService).resolveUnit(any());
    }

    // --- list ---

    @Test
    void list_returnsMappedRecipes() {
        Recipe recipe = new Recipe();
        RecipeResponseDTO dto = buildResponseDTO();
        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(recipe)));
        when(recipeMapper.toDTO(recipe)).thenReturn(dto);

        Collection<RecipeResponseDTO> result = recipeService.list(30);

        assertThat(result).hasSize(1);
        assertThat(result.iterator().next().getId()).isEqualTo(1L);
    }

    @Test
    void list_returnsEmpty_whenNoRecipes() {
        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(List.of()));

        Collection<RecipeResponseDTO> result = recipeService.list(30);

        assertThat(result).isEmpty();
    }

    // --- get ---

    @Test
    void get_found() {
        Recipe recipe = new Recipe();
        RecipeResponseDTO dto = buildResponseDTO();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeMapper.toDTO(recipe)).thenReturn(dto);

        RecipeResponseDTO result = recipeService.get(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void get_notFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.get(99L))
                .isInstanceOf(NotFoundException.class);
    }

    // --- delete ---

    @Test
    void delete_success() {
        Recipe recipe = new Recipe();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        recipeService.delete(1L);

        verify(recipeRepository).deleteById(1L);
    }

    @Test
    void delete_notFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.delete(99L))
                .isInstanceOf(NotFoundException.class);
    }

    // --- search ---

    @Test
    void search_returnsMatchingRecipes() {
        Recipe recipe = new Recipe();
        recipe.setName("Pasta Bolognese");
        RecipeResponseDTO dto = buildResponseDTO();
        when(recipeRepository.findByNameContainingIgnoreCase("pasta")).thenReturn(List.of(recipe));
        when(recipeMapper.toDTO(recipe)).thenReturn(dto);

        List<RecipeResponseDTO> result = recipeService.search("pasta");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    void search_returnsEmpty_whenNoMatches() {
        when(recipeRepository.findByNameContainingIgnoreCase("xyz")).thenReturn(List.of());

        List<RecipeResponseDTO> result = recipeService.search("xyz");

        assertThat(result).isEmpty();
    }

    // --- update ---

    @Test
    void update_success() {
        Recipe recipe = new Recipe();
        RecipeResponseDTO dto = buildResponseDTO();
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeMapper.toDTO(recipe)).thenReturn(dto);

        RecipeResponseDTO result = recipeService.update(new RecipeUpdateDTO(), 1L);

        assertThat(result.getId()).isEqualTo(1L);
        verify(recipeMapper).updateRecipeFromDTO(any(), any());
        verify(recipeRepository, org.mockito.Mockito.atLeastOnce()).flush();
    }

    @Test
    void update_notFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> recipeService.update(new RecipeUpdateDTO(), 99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }
}
