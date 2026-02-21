package com.foodiesfinds.recipe_service.dto.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id"})
public abstract class BaseRecipeDTO<
    I extends BaseRecipeIngredientDTO,
    T extends BaseRecipeTagDTO,
    S extends BaseInstructionStepDTO
    > {

  @NotEmpty(message = "Recipe must have a name.")
  @NotNull(message = "Recipe name is required.")
  @Size(min = 3, max = 150, message = "Name must be between 3 and 150 characters.")
  private String name;
  private String description;
  @Positive(message = "Calories must be a positive value.")
  private Short calories;

  @Positive(message = "Servings must be a more than zero.")
  private short servings;

  @Min(value = 0, message = "Cooking time cannot be negative.")
  private short cookingTime;

  @Min(value = 1, message = "Preparation time must be at least 1 minute.")
  private short preparationTime;
  private CuisineResponseDTO cuisine;
  private List<T> tags = new ArrayList<>();
  private String author;

  private List<I> ingredients = new ArrayList<>();

  private List<S> steps = new ArrayList<>();

  private String imageUrl;

}
