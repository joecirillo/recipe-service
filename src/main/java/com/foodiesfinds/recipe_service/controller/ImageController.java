package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/recipes/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ResponseFactory response;

    @PostMapping
    public ResponseEntity<Response> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = imageService.upload(file);
        return response.buildResponse(OK, "Image uploaded", url);
    }

    @DeleteMapping
    public ResponseEntity<Response> deleteImage(@RequestParam("key") String key) {
        imageService.delete(key);
        return response.buildResponse(NO_CONTENT, "Image deleted", null);
    }
}
