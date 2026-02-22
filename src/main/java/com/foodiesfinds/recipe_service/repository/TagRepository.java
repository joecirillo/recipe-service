package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameIgnoreCase(String tagName);
}
