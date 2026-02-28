package com.foodiesfinds.recipe_service.dto.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id"})
public abstract class BaseRecipeDTO<
        I extends BaseRecipeIngredientDTO,
        T extends BaseRecipeTagDTO,
        S extends BaseInstructionStepDTO> {
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters.")
    private String name;
    private String description;
    private Short calories;
    private short servings;
    private short cookingTime;
    private short preparationTime;
    private CuisineResponseDTO cuisine;
    private List<T> tags = new ArrayList<>();
    private String author;
    private List<I> ingredients = new ArrayList<>();
    private List<S> steps = new ArrayList<>();
    private String imageUrl;
}
