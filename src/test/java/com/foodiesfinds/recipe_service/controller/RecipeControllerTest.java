package com.foodiesfinds.recipe_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
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
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class})
@ActiveProfiles("test")
class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RecipeService recipeService;

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
    void getRecipes() throws Exception {
        when(recipeService.list(30)).thenReturn(List.of(buildResponseDTO(), buildResponseDTO()));

        mockMvc.perform(get("/recipe/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void saveRecipe() throws Exception {
        when(recipeService.save(any())).thenReturn(buildResponseDTO());

        mockMvc.perform(post("/recipe/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildValidSaveDTO())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void saveRecipe_validationFailure() throws Exception {
        mockMvc.perform(post("/recipe/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getRecipe() throws Exception {
        when(recipeService.get(1L)).thenReturn(buildResponseDTO());

        mockMvc.perform(get("/recipe/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recipe retrieved"));
    }

    @Test
    void getRecipe_notFound() throws Exception {
        when(recipeService.get(99L)).thenThrow(new NotFoundException("Recipe not found"));

        mockMvc.perform(get("/recipe/get/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateRecipe() throws Exception {
        when(recipeService.update(any(), eq(1L))).thenReturn(buildResponseDTO());

        mockMvc.perform(patch("/recipe/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RecipeUpdateDTO())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Recipe updated"));
    }

    @Test
    void deleteRecipe() throws Exception {
        doNothing().when(recipeService).delete(1L);

        mockMvc.perform(delete("/recipe/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteRecipe_notFound() throws Exception {
        doThrow(new NotFoundException("Recipe not found")).when(recipeService).delete(99L);

        mockMvc.perform(delete("/recipe/delete/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchRecipe() throws Exception {
        when(recipeService.search("pasta")).thenReturn(List.of(buildResponseDTO()));

        mockMvc.perform(get("/recipe/search").param("query", "pasta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchRecipe_emptyResults() throws Exception {
        when(recipeService.search("xyz")).thenReturn(List.of());

        mockMvc.perform(get("/recipe/search").param("query", "xyz"))
                .andExpect(status().isOk());
    }
}
