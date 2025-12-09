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

  @NotNull(message = "ID must be provided for update operation.")
  private Long id;

  private List<IngredientDTO> ingredients = new ArrayList<>();

  private List<InstructionStepDTO> steps = new ArrayList<>();
  private List<TagDTO> tags = new ArrayList<>();

  @NotEmpty(message = "Recipe must have a name.")
  @NotNull(message = "Recipe name is required.")
  @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters.")
  private String recipeName;
  private String description;
  private String author;
  private Long cuisineId;
  private String cuisineName;

  @Positive(message = "Calories must be a positive value.")
  private short calories;

  @Min(value = 1, message = "Cooking time must be at least 1 minute.")
  private short cookingTime;
  private short preparationTime;
  private short servings;
  private String imageUrl;
  private LocalDate createdAt;
  private LocalDateTime updatedAt;

}
