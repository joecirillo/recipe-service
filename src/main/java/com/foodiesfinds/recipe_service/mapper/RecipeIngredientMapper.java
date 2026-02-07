package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.dto.RecipeIngredientDTO;
import com.foodiesfinds.recipe_service.dto.RecipeIngredientSaveDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

  @Mapping(target = "ingredient.id", source = "id")
  @Mapping(target = "ingredient.name", source = "name")
  RecipeIngredient toEntity(RecipeIngredientDTO dto);

  @Mapping(target = "id", source = "ingredient.id")
  @Mapping(target = "name", source = "ingredient.name")
  @Mapping(target = "unitId", source = "unit.id")
  @Mapping(target = "unitName", source = "unit.name")
  @Mapping(target = "abbreviation", source = "unit.abbreviation")
  RecipeIngredientDTO toDTO(RecipeIngredient entity);

  @Mapping(target = "unit.id", source = "unitId")
  @Mapping(target = "unit.name", source = "unitName")
  @Mapping(target = "unit.abbreviation", source = "abbreviation")
  @Mapping(target = "ingredient.id", source = "id")
  @Mapping(target = "ingredient.name", source = "name")
  RecipeIngredient toEntity(RecipeIngredientSaveDTO dto);

  default Ingredient map(Long id) {
    if (id == null) return null;
    Ingredient i = new Ingredient();
    i.setId(id);
    return i;
  }
}
