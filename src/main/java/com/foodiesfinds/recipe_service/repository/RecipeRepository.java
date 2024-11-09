package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}