package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Composite key class for RecipeTag entity.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeTagId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "recipe_id")
  private Long recipeId;

  @Column(name = "tag_id")
  private Long tagId;

}
