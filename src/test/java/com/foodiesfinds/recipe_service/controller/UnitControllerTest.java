package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.config.ApiKeyProperties;
import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.filter.ApiKeyFilter;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.service.UnitService;
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

@WebMvcTest(UnitController.class)
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class, ApiKeyFilter.class, ApiKeyProperties.class})
@ActiveProfiles("test")
class UnitControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UnitService unitService;

    @Test
    void getUnits() throws Exception {
        when(unitService.list()).thenReturn(List.of(new NamedEntityDTO(1L, "cup")));

        mockMvc.perform(get("/unit/list").header("X-Api-Key", "test-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void missingApiKey_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/unit/list"))
                .andExpect(status().isUnauthorized());
    }
}
