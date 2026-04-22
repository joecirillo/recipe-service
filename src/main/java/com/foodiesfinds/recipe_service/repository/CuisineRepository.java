package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

    Optional<Cuisine> findByNameIgnoreCase(String name);

    List<Cuisine> findByNameContainingIgnoreCase(String name);

}
