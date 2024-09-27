package com.foodiesfinds.recipe_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @NotNull
  @Column(length = 255)
  private String recipeName;

  @NotNull
  @Column(length = 255)
  private String author;

  @NotNull
  private short preparationTime;

  @NotNull
  private short cookingTime;

  @Column(length = 255)
  private String cuisine;

  @NotNull
  private short servings;

  @NotNull
  private short calories;

  @Column(name = "ingredients", columnDefinition = "VARCHAR(255)[]")
  private List<String> ingredients;

  @Column(name = "directions", columnDefinition = "TEXT[]")
  private List<String> directions;

  @Column(name = "tags", columnDefinition = "VARCHAR(255)[]")
  private List<String> tags;

  @NotNull
  private LocalDate created;

  @PrePersist
  protected void onCreate() {
    created = LocalDate.now(); // Automatically sets the date before persisting
  }

  @Column(length = 255)
  private String picture;

}

