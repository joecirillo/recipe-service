package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "recipe_tags", schema = "recipeservice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeTag {

  @EmbeddedId
  private RecipeTagId id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId("recipeId")
  @JoinColumn(
      name = "recipe_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_recipe_tags_recipe_id")
  )
  @OnDelete(action = OnDeleteAction.CASCADE) // mirrors the DB ON DELETE CASCADE for recipes
  private Recipe recipe;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @MapsId("tagId")
  @JoinColumn(
      name = "tag_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_recipe_tags_tag_id")
  )
  private Tag tag;

  public RecipeTag(Recipe recipe, Tag tag) {
    this.recipe = recipe;
    this.tag = tag;
    this.id = new RecipeTagId(
        recipe == null ? null : recipe.getId(),
        tag == null ? null : tag.getTagId()
    );
  }
}
