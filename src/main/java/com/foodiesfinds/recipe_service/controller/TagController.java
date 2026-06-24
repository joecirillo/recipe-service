package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final ResponseFactory response;

    @GetMapping
    public ResponseEntity<Response> getTags(@RequestParam(required = false) String query) {
        if (query == null || query.isBlank()) {
            return response.buildResponse(OK, "Tags retrieved", tagService.list());
        }
        return response.buildResponse(OK, "Tags queried", tagService.search(query));
    }

}
