package com.foodiesfinds.recipe_service.controller;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.foodiesfinds.recipe_service.model.Recipe;
import com.foodiesfinds.recipe_service.model.Response;
import com.foodiesfinds.recipe_service.service.implementation.RecipeServiceImpl;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
  public ResponseEntity<Response> saveRecipe(@RequestBody  @Valid Recipe recipe) {
    return ResponseEntity.ok
        (Response.builder()
            .timeStamp(now())
            .data(Map.of("recipes", recipeService.create(recipe)))
            .message("Recipe created")
            .status(CREATED)
            .statusCode(CREATED.value())
            .build());
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

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Response> deleteRecipe(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(now())
            .data(Map.of("deleted", recipeService.delete(id)))
            .message("Recipe deleted")
            .status(OK)
            .statusCode(OK.value())
            .build()
    );
  }

  @GetMapping("search/{query}")
  public ResponseEntity<Response> searchRecipe(@PathVariable("query") String query) {
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
