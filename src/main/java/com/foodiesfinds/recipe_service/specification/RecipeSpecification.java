package com.foodiesfinds.recipe_service.specification;

import com.foodiesfinds.recipe_service.dto.RecipeSearchParams;
import com.foodiesfinds.recipe_service.entity.Recipe;
import com.foodiesfinds.recipe_service.entity.RecipeIngredient;
import com.foodiesfinds.recipe_service.entity.RecipeTag;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class RecipeSpecification {

    private RecipeSpecification() {}

    public static Specification<Recipe> hasNameContaining(String name) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Recipe> hasCuisineContaining(String cuisine) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.join("cuisine", JoinType.INNER).get("name")),
                        "%" + cuisine.toLowerCase() + "%");
    }

    public static Specification<Recipe> hasTagContaining(String tag) {
        return (root, query, cb) -> {
            if (query != null) query.distinct(true);
            Join<Recipe, RecipeTag> tags = root.join("tags", JoinType.INNER);
            return cb.like(cb.lower(tags.join("tag", JoinType.INNER).get("name")),
                    "%" + tag.toLowerCase() + "%");
        };
    }

    public static Specification<Recipe> hasIngredientContaining(String ingredient) {
        return (root, query, cb) -> {
            if (query != null) query.distinct(true);
            Join<Recipe, RecipeIngredient> ingredients = root.join("ingredients", JoinType.INNER);
            return cb.like(cb.lower(ingredients.join("ingredient", JoinType.INNER).get("name")),
                    "%" + ingredient.toLowerCase() + "%");
        };
    }

    public static Specification<Recipe> hasCuisineId(Long cuisineId) {
        return (root, query, cb) ->
                cb.equal(root.join("cuisine", JoinType.INNER).get("id"), cuisineId);
    }

    public static Specification<Recipe> hasTagId(Long tagId) {
        return (root, query, cb) -> {
            if (query != null) query.distinct(true);
            Join<Recipe, RecipeTag> tags = root.join("tags", JoinType.INNER);
            return cb.equal(tags.join("tag", JoinType.INNER).get("id"), tagId);
        };
    }

    public static Specification<Recipe> hasIngredientId(Long ingredientId) {
        return (root, query, cb) -> {
            if (query != null) query.distinct(true);
            Join<Recipe, RecipeIngredient> ingredients = root.join("ingredients", JoinType.INNER);
            return cb.equal(ingredients.join("ingredient", JoinType.INNER).get("id"), ingredientId);
        };
    }

    public static Specification<Recipe> buildFrom(RecipeSearchParams params) {
        Specification<Recipe> spec = Specification.where(null);
        if (params.name() != null && !params.name().isBlank()) {
            spec = spec.and(hasNameContaining(params.name()));
        }
        if (params.tag() != null && !params.tag().isBlank()) {
            spec = spec.and(hasTagContaining(params.tag()));
        }
        if (params.cuisine() != null && !params.cuisine().isBlank()) {
            spec = spec.and(hasCuisineContaining(params.cuisine()));
        }
        if (params.ingredient() != null && !params.ingredient().isBlank()) {
            spec = spec.and(hasIngredientContaining(params.ingredient()));
        }
        if (params.tagId() != null) {
            spec = spec.and(hasTagId(params.tagId()));
        }
        if (params.cuisineId() != null) {
            spec = spec.and(hasCuisineId(params.cuisineId()));
        }
        if (params.ingredientId() != null) {
            spec = spec.and(hasIngredientId(params.ingredientId()));
        }
        return spec;
    }
}
