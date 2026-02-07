package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "recipes", schema = "recipeservice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(name = "recipe_name", length = 255, nullable = false)
  private String name;

  @Column(columnDefinition = "text")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "cuisine_id", nullable = false)
  private Cuisine cuisine;

  @NotNull
  @Column(length = 255)
  private String author;

  @NotNull
  private short calories;

  @NotNull
  private short servings;

  @NotNull
  private short cookingTime;

  @NotNull
  private short preparationTime;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "recipe_id", nullable = false)
  private List<RecipeIngredient> ingredients = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "recipe_id", nullable = false)
  private List<RecipeInstructionStep> steps = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "recipe_id", nullable = false)
  private List<RecipeTag> tags = new ArrayList<>();

  @Column(name = "image_url", length = 255)
  private String imageUrl;

  @NotNull
  @Column(name = "created_at", nullable = false)
  private LocalDate createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    if (createdAt == null) {
      createdAt = LocalDate.now(); // Automatically sets the date before persisting
    }
    updatedAt = LocalDateTime.now();
  }

}
