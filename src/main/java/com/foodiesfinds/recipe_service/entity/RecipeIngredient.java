package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "recipe_ingredients",
    schema = "recipeservice",
    uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "ingredient_id", "unit_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recipe_ingredient_id")
  private Long recipeIngredientId;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "recipe_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_recipe_ingredients_recipe_id")
  )
  private Recipe recipe;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "ingredient_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_recipe_ingredients_ingredient_id")
  )
  private Ingredient ingredient;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(
      name = "unit_id",
      foreignKey = @ForeignKey(name = "fk_recipe_ingredients_unit_id")
  )
  private Unit unit;

  @NotNull
  @Column(name = "quantity", precision = 8, scale = 2, nullable = false)
  private BigDecimal quantity;

  @Column(name = "notes", length = 255)
  private String notes;
}