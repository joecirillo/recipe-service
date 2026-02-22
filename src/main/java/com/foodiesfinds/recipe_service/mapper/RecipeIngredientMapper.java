package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientResponseDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientSaveDTO;
import com.foodiesfinds.recipe_service.dto.ingredient.RecipeIngredientUpdateDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

    @Mapping(target = "ingredient.id", source = "id")
    @Mapping(target = "ingredient.name", source = "name")
    RecipeIngredient toEntity(RecipeIngredientResponseDTO dto);

    @Mapping(target = "id", source = "ingredient.id")
    @Mapping(target = "name", source = "ingredient.name")
    @Mapping(target = "unitId", source = "unit.id")
    @Mapping(target = "unitName", source = "unit.name")
    @Mapping(target = "abbreviation", source = "unit.abbreviation")
    RecipeIngredientResponseDTO toDTO(RecipeIngredient entity);

    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "ingredient.id", source = "id")
    @Mapping(target = "ingredient.name", source = "name")
    RecipeIngredient toEntity(RecipeIngredientSaveDTO dto);

    RecipeIngredientUpdateDTO toUpdateDTO(RecipeIngredient entity);

    @Mapping(target = "unit.id", source = "unitId")
    @Mapping(target = "ingredient.id", source = "id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "ingredient.name", source = "name")
    RecipeIngredient toEntity(RecipeIngredientUpdateDTO dto);

    default Ingredient map(Long id) {
        if (id == null) return null;
        Ingredient i = new Ingredient();
        i.setId(id);
        return i;
    }
}
