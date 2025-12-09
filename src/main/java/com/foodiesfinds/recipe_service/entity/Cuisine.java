package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cuisines", schema = "recipeservice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuisine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cuisine_id")
  Short cuisineId;

  @Column(name = "cuisine", length = 100, nullable = false, unique = true)
  String cuisineName;

  @OneToMany(mappedBy = "cuisine", fetch = FetchType.LAZY)
  Set<Recipe> recipes;

}
