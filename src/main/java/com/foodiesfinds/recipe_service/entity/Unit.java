package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "units", schema = "recipeservice")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "unit_id")
  private Long unitId;

  @NotNull
  @Column(name = "unit_name", length = 50, nullable = false, unique = true)
  private String unitName;

  @Column(name = "abbreviation", length = 10)
  private String abbreviation;

}
