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

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    private final ResponseFactory response;

    @GetMapping("/list")
    public ResponseEntity<Response> getRecipes() {
        return response.buildResponse(OK, "Recipes retrieved",
                recipeService.list(30));
    }

    @PostMapping("/save")
    public ResponseEntity<RecipeResponseDTO> saveRecipe(@RequestBody @Valid RecipeSaveDTO recipe) {

        RecipeResponseDTO savedRecipe = recipeService.save(recipe);

        java.net.URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(savedRecipe.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(savedRecipe);
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
            @RequestParam(required = false) String ingredient) {
        return response.buildResponse(OK, "Recipes queried",
                recipeService.search(new RecipeSearchParams(name, tag, cuisine, ingredient)));
    }

}
