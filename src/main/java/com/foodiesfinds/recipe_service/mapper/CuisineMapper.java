package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.CuisineDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CuisineMapper {

  Cuisine toEntity(CuisineDTO name);

  String toDTO(Cuisine entity);

}
