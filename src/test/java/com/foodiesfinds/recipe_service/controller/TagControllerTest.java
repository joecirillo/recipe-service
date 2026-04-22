package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.tag.TagResponseDTO;
import com.foodiesfinds.recipe_service.service.TagService;
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

@WebMvcTest(TagController.class)
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class})
@ActiveProfiles("test")
class TagControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TagService tagService;

    private TagResponseDTO buildTagDTO() {
        TagResponseDTO dto = new TagResponseDTO();
        dto.setTagId(1L);
        dto.setTagName("vegan");
        return dto;
    }

    @Test
    void getTags() throws Exception {
        when(tagService.list()).thenReturn(List.of(buildTagDTO()));

        mockMvc.perform(get("/tag/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchTags() throws Exception {
        when(tagService.search("veg")).thenReturn(List.of(new NamedEntityDTO(1L, "vegan")));

        mockMvc.perform(get("/tag/search").param("query", "veg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void searchTags_emptyResults() throws Exception {
        when(tagService.search("xyz")).thenReturn(List.of());

        mockMvc.perform(get("/tag/search").param("query", "xyz"))
                .andExpect(status().isOk());
    }
}
