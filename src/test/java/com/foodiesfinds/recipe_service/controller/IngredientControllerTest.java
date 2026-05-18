package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.config.ApiKeyProperties;
import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.filter.ApiKeyFilter;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.IngredientResponseDTO;
import com.foodiesfinds.recipe_service.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class, ApiKeyFilter.class, ApiKeyProperties.class})
@ActiveProfiles("test")
class IngredientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IngredientService ingredientService;

    private IngredientResponseDTO buildIngredientDTO() {
        IngredientResponseDTO dto = new IngredientResponseDTO();
        dto.setId(1L);
        dto.setName("flour");
        return dto;
    }

    @Test
    void getIngredients() throws Exception {
        when(ingredientService.list()).thenReturn(List.of(buildIngredientDTO()));

        mockMvc.perform(get("/ingredient/list").header("X-Api-Key", "test-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchIngredients() throws Exception {
        when(ingredientService.search("flo")).thenReturn(List.of(new NamedEntityDTO(1L, "flour")));

        mockMvc.perform(get("/ingredient/search").header("X-Api-Key", "test-api-key").param("query", "flo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchIngredients_emptyResults() throws Exception {
        when(ingredientService.search("xyz")).thenReturn(List.of());

        mockMvc.perform(get("/ingredient/search").header("X-Api-Key", "test-api-key").param("query", "xyz"))
                .andExpect(status().isOk());
    }

    @Test
    void missingApiKey_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/ingredient/list"))
                .andExpect(status().isUnauthorized());
    }
}
