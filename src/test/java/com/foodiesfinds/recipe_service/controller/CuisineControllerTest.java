package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.service.CuisineService;
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

@WebMvcTest(CuisineController.class)
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class})
@ActiveProfiles("test")
class CuisineControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CuisineService cuisineService;

    private CuisineResponseDTO buildCuisineDTO() {
        CuisineResponseDTO dto = new CuisineResponseDTO();
        dto.setId(1L);
        dto.setName("Italian");
        return dto;
    }

    @Test
    void getCuisines() throws Exception {
        when(cuisineService.list()).thenReturn(List.of(buildCuisineDTO()));

        mockMvc.perform(get("/cuisine/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchCuisines() throws Exception {
        when(cuisineService.search("ital")).thenReturn(List.of(new NamedEntityDTO(1L, "Italian")));

        mockMvc.perform(get("/cuisine/search").param("query", "ital"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchCuisines_emptyResults() throws Exception {
        when(cuisineService.search("xyz")).thenReturn(List.of());

        mockMvc.perform(get("/cuisine/search").param("query", "xyz"))
                .andExpect(status().isOk());
    }
}
