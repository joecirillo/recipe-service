package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.RecipeSearchParams;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    private final ResponseFactory response;

    @GetMapping("/list")
    public ResponseEntity<Response> getRecipes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int limit) {
        return response.buildResponse(OK, "Recipes retrieved",
                recipeService.list(page, limit));
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveRecipe(@RequestBody @Valid RecipeSaveDTO recipe) {

        RecipeResponseDTO savedRecipe = recipeService.save(recipe);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedRecipe.getId())
                .toUri();

        return response.buildResponse(CREATED, "Recipe saved", savedRecipe, location);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getRecipe(@PathVariable("id") Long id) {
        return response.buildResponse(OK, "Recipe retrieved",
                recipeService.get(id));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Response> updateRecipe(@RequestBody @Valid RecipeUpdateDTO recipe,
                                                 @PathVariable("id") Long id) {
        return response.buildResponse(OK, "Recipe updated",
                recipeService.update(recipe, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchRecipes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) String ingredient,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) Long cuisineId,
            @RequestParam(required = false) Long ingredientId) {
        return response.buildResponse(OK, "Recipes queried",
                recipeService.search(new RecipeSearchParams(name, tag, cuisine, ingredient, tagId, cuisineId, ingredientId)));
    }

}
