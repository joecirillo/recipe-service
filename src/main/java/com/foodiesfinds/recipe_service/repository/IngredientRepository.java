package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Ingredient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

  boolean existsByName(String name);

  Optional<Ingredient> findByNameIgnoreCase(String name);
}
