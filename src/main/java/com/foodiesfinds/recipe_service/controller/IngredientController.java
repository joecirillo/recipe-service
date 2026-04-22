package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;
    private final ResponseFactory response;

    @GetMapping("/list")
    public ResponseEntity<Response> getIngredients() {
        return response.buildResponse(OK, "Ingredients retrieved", ingredientService.list());
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchIngredients(@RequestParam("query") String query) {
        return response.buildResponse(OK, "Ingredients queried", ingredientService.search(query));
    }

}
