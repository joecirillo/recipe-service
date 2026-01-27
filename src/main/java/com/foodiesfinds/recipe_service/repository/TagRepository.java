package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

  boolean existsByTagName(String tagName);
}
