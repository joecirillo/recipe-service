package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.entity.Cuisine;

public interface CuisineService {

    Cuisine resolveCuisine(Cuisine requestedCuisine);

}
