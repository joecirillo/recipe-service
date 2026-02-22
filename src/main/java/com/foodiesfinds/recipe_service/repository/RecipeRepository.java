package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByNameContainingIgnoreCase(String query);
}