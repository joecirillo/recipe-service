package com.foodiesfinds.recipe_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodiesfinds.recipe_service.core.config.ApiKeyProperties;
import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.core.filter.ApiKeyFilter;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeListItemDTO;
import com.foodiesfinds.recipe_service.dto.RecipeSearchParams;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientSaveDTO;
import com.foodiesfinds.recipe_service.dto.instruction.InstructionStepSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecipeController.class)
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class, ApiKeyFilter.class, ApiKeyProperties.class})
@ActiveProfiles("test")
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RecipeService recipeService;

    private RecipeListItemDTO buildNamedDTO() {
        return new RecipeListItemDTO(1L, "Pasta", null);
    }

    private RecipeResponseDTO buildResponseDTO() {
        RecipeResponseDTO dto = new RecipeResponseDTO();
        dto.setId(1L);
        dto.setName("Pasta");
        return dto;
    }

    private RecipeSaveDTO buildValidSaveDTO() {
        RecipeSaveDTO dto = new RecipeSaveDTO();
        dto.setName("Pasta");
        dto.setCalories((short) 500);
        dto.setServings((short) 2);
        dto.setCookingTime((short) 15);
        dto.setPreparationTime((short) 10);
        CuisineResponseDTO cuisine = new CuisineResponseDTO();
        cuisine.setName("Italian");
        dto.setCuisine(cuisine);

        RecipeIngredientSaveDTO ingredient = new RecipeIngredientSaveDTO();
        ingredient.setName("Noodles");
        dto.setIngredients(List.of(ingredient));

        InstructionStepSaveDTO step = new InstructionStepSaveDTO();
        step.setStepNumber((short) 1);
        step.setDescription("Boil water");
        dto.setSteps(List.of(step));

        return dto;
    }

    @Test
    void getRecipes_defaultPagination() throws Exception {
        when(recipeService.list(0, 12)).thenReturn(List.of(buildNamedDTO(), buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void getRecipes_customPagination() throws Exception {
        when(recipeService.list(1, 6)).thenReturn(List.of(buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key")
                        .param("page", "1").param("limit", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void saveRecipe() throws Exception {
        when(recipeService.save(any())).thenReturn(buildResponseDTO());

        mockMvc.perform(post("/recipes")
                        .header("X-Api-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildValidSaveDTO())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.message").value("Recipe saved"))
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void saveRecipe_validationFailure() throws Exception {
        mockMvc.perform(post("/recipes")
                        .header("X-Api-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRecipe() throws Exception {
        when(recipeService.get(1L)).thenReturn(buildResponseDTO());

        mockMvc.perform(get("/recipes/1").header("X-Api-Key", "test-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recipe retrieved"));
    }

    @Test
    void getRecipe_notFound() throws Exception {
        when(recipeService.get(99L)).thenThrow(new NotFoundException("Recipe not found"));

        mockMvc.perform(get("/recipes/99").header("X-Api-Key", "test-api-key"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRecipe() throws Exception {
        when(recipeService.update(any(), eq(1L))).thenReturn(buildResponseDTO());

        mockMvc.perform(patch("/recipes/1")
                        .header("X-Api-Key", "test-api-key")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RecipeUpdateDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recipe updated"));
    }

    @Test
    void deleteRecipe() throws Exception {
        doNothing().when(recipeService).delete(1L);

        mockMvc.perform(delete("/recipes/1").header("X-Api-Key", "test-admin-key"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecipe_notFound() throws Exception {
        doThrow(new NotFoundException("Recipe not found")).when(recipeService).delete(99L);

        mockMvc.perform(delete("/recipes/99").header("X-Api-Key", "test-admin-key"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRecipe_userKeyRejected() throws Exception {
        mockMvc.perform(delete("/recipes/1").header("X-Api-Key", "test-api-key"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void searchRecipes_byName() throws Exception {
        when(recipeService.search(new RecipeSearchParams("pasta", null, null, null, null, null, null)))
                .thenReturn(List.of(buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key").param("name", "pasta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchRecipes_byTag() throws Exception {
        when(recipeService.search(new RecipeSearchParams(null, "vegan", null, null, null, null, null)))
                .thenReturn(List.of(buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key").param("tag", "vegan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchRecipes_byCuisine() throws Exception {
        when(recipeService.search(new RecipeSearchParams(null, null, "italian", null, null, null, null)))
                .thenReturn(List.of(buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key").param("cuisine", "italian"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchRecipes_byIngredient() throws Exception {
        when(recipeService.search(new RecipeSearchParams(null, null, null, "flour", null, null, null)))
                .thenReturn(List.of(buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key").param("ingredient", "flour"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchRecipes_multipleFilters() throws Exception {
        when(recipeService.search(new RecipeSearchParams("pasta", "vegan", null, null, null, null, null)))
                .thenReturn(List.of(buildNamedDTO()));

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key").param("name", "pasta").param("tag", "vegan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchRecipes_emptyResults() throws Exception {
        when(recipeService.search(new RecipeSearchParams("xyz", null, null, null, null, null, null)))
                .thenReturn(List.of());

        mockMvc.perform(get("/recipes").header("X-Api-Key", "test-api-key").param("name", "xyz"))
                .andExpect(status().isOk());
    }

    @Test
    void missingApiKey_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isUnauthorized());
    }
}
