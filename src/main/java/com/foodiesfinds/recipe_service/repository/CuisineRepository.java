package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Cuisine;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

  Optional<Cuisine> findByNameIgnoreCase(String name);

}
