package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe_instruction_steps", schema = "recipeservice", uniqueConstraints = @UniqueConstraint(columnNames = {"recipe_id", "step_number"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeInstructionStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long stepId;

    @NotNull
    @Column(name = "step_number", nullable = false)
    private Short stepNumber;

    @NotNull
    @Column(name = "description", columnDefinition = "text", nullable = false)
    private String description;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "tip", columnDefinition = "text")
    private String tip;
}