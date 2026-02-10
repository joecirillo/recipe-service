package com.foodiesfinds.recipe_service.repository;

import com.foodiesfinds.recipe_service.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByNameIgnoreCase(String tagName);
}
