package com.foodiesfinds.recipe_service.dto.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeListItemDTO {
    private Long id;
    private String name;
    private String imageUrl;
}
