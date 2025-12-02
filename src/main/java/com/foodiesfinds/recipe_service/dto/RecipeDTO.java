package com.foodiesfinds.recipe_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class RecipeDTO {

  private Long id;
  private List<IngredientDTO> ingredients = new ArrayList<>();
  private List<InstructionStepDTO> steps = new ArrayList<>();
  private List<TagDTO> tags = new ArrayList<>();
  private String recipeName;
  private String description;
  private String author;
  private String cuisine;
  private short calories;
  private short cookingTime;
  private short preparationTime;
  private short servings;
  private String imageUrl;
  private LocalDate createdAt;
  private LocalDateTime updatedAt;

}
