package com.foodiesfinds.recipe_service.dto;

public record RecipeSearchParams(String name, String tag, String cuisine, String ingredient,
                                 Long tagId, Long cuisineId, Long ingredientId) {
}
