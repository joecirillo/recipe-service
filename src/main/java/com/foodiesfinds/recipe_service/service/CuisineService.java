package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;

import java.util.List;

public interface CuisineService {

    Cuisine resolveCuisine(Cuisine requestedCuisine);

    List<CuisineResponseDTO> list();

    List<NamedEntityDTO> search(String query);

}
