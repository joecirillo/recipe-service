package com.foodiesfinds.recipe_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RecipeDTO {

  private Long id;

  @NotEmpty(message = "Recipe must have a name.")
  @NotNull(message = "Recipe name is required.")
  @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters.")
  private String name;
  private String description;
  @Positive(message = "Calories must be a positive value.")
  private short calories;

  @Positive(message = "Servings must be a more than zero.")
  private short servings;

  @Min(value = 0, message = "Cooking time cannot be negative.")
  private short cookingTime;

  @Min(value = 1, message = "Preparation time must be at least 1 minute.")
  private short preparationTime;
  private CuisineDTO cuisine;
  private List<RecipeTagDTO> tags = new ArrayList<>();
  private String author;

  @NotNull(message = "Recipe must have at least one ingredient.")
  private List<RecipeIngredientDTO> ingredients = new ArrayList<>();

  @NotNull(message = "Recipe must have at least one instruction step.")
  private List<InstructionStepDTO> steps = new ArrayList<>();

  private String imageUrl;
  private LocalDate createdAt;
  private LocalDateTime updatedAt;

}
