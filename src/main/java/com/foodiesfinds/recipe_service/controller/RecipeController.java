package com.foodiesfinds.recipe_service.controller;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeResponseDTO;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeSaveDTO;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.dto.recipe.RecipeUpdateDTO;
import com.foodiesfinds.recipe_service.service.implementation.RecipeServiceImpl;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

  private final RecipeServiceImpl recipeService;

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
  public ResponseEntity<Response> searchRecipe(@RequestParam("query") String query) {
    return response.buildResponse(OK, "Recipe queried",
        recipeService.search(query));
  }

}
