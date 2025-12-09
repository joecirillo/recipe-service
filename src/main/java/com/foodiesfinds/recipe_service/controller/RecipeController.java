package com.foodiesfinds.recipe_service.controller;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.foodiesfinds.recipe_service.dto.RecipeDTO;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.dto.Response;
import com.foodiesfinds.recipe_service.service.implementation.RecipeServiceImpl;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/list")
  public ResponseEntity<Response> getRecipes() {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("recipes", recipeService.list(30)))
            .message("Recipes retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build());
  }

  @PostMapping("/save")
  public ResponseEntity<RecipeDTO> saveRecipe(@RequestBody @Valid RecipeDTO recipe) {

    RecipeDTO savedRecipe = recipeService.save(recipe);

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
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("recipe", recipeService.get(id)))
            .message("Recipe retrieved")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }

  @PutMapping("/update")
  public ResponseEntity<Response> updateRecipe(@RequestBody @Valid RecipeDTO recipe) {
    recipeService.update(recipe);
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("recipe", recipeService.update(recipe)))
            .message("Recipe updated")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }


  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteRecipe(@PathVariable("id") Long id) {
    recipeService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<Response> searchRecipe(@RequestParam("query") String query) {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("queried", recipeService.search(query)))
            .message("Recipe queried")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }

}
