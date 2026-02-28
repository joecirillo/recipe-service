package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @NotNull
    @Column(name = "quantity", precision = 8, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "notes", length = 255)
    private String notes;
}