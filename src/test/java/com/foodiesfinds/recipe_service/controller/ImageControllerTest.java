package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.config.ApiKeyProperties;
import com.foodiesfinds.recipe_service.core.config.S3Properties;
import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.GlobalExceptionHandler;
import com.foodiesfinds.recipe_service.core.filter.ApiKeyFilter;
import com.foodiesfinds.recipe_service.core.response.ErrorResponseFactory;
import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.service.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
@Import({ResponseFactory.class, GlobalExceptionHandler.class, ErrorResponseFactory.class, ApiKeyFilter.class, ApiKeyProperties.class, S3Properties.class})
@ActiveProfiles("test")
class ImageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ImageService imageService;

    @Test
    void uploadImage_success() throws Exception {
        String expectedUrl = "https://test-bucket.s3.us-west-2.amazonaws.com/recipes/some-uuid.jpg";
        when(imageService.upload(any())).thenReturn(expectedUrl);

        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "fake-image-bytes".getBytes()
        );

        mockMvc.perform(multipart("/recipe/image")
                        .file(file)
                        .header("X-Api-Key", "test-api-key"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(expectedUrl));
    }

    @Test
    void uploadImage_noFile_returnsBadRequest() throws Exception {
        when(imageService.upload(any())).thenThrow(new BadRequestException("File must not be empty"));

        MockMultipartFile file = new MockMultipartFile(
                "file", "empty.jpg", "image/jpeg", new byte[0]
        );

        mockMvc.perform(multipart("/recipe/image")
                        .file(file)
                        .header("X-Api-Key", "test-api-key"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void uploadImage_invalidContentType_returnsBadRequest() throws Exception {
        when(imageService.upload(any())).thenThrow(new BadRequestException("Unsupported file type. Allowed: jpeg, png, webp, gif, heic, heif"));

        MockMultipartFile file = new MockMultipartFile(
                "file", "document.pdf", "application/pdf", "pdf-bytes".getBytes()
        );

        mockMvc.perform(multipart("/recipe/image")
                        .file(file)
                        .header("X-Api-Key", "test-api-key"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void uploadImage_missingApiKey_returnsUnauthorized() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "fake-image-bytes".getBytes()
        );

        mockMvc.perform(multipart("/recipe/image")
                        .file(file))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteImage_success() throws Exception {
        doNothing().when(imageService).delete(any());

        mockMvc.perform(delete("/recipe/image")
                        .param("key", "recipes/some-uuid.jpg")
                        .header("X-Api-Key", "test-api-key"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteImage_invalidKey_returnsBadRequest() throws Exception {
        doThrow(new BadRequestException("Invalid image key")).when(imageService).delete(any());

        mockMvc.perform(delete("/recipe/image")
                        .param("key", "other/some-file.jpg")
                        .header("X-Api-Key", "test-api-key"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteImage_missingApiKey_returnsUnauthorized() throws Exception {
        mockMvc.perform(delete("/recipe/image")
                        .param("key", "recipes/some-uuid.jpg"))
                .andExpect(status().isUnauthorized());
    }
}
