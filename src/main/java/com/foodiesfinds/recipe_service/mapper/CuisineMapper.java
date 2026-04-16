package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CuisineMapper {

    Cuisine toEntity(CuisineResponseDTO name);

    CuisineResponseDTO toDTO(Cuisine entity);

}
