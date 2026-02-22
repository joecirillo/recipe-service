package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.*;
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
    private Long id;

    @NotNull
    @Column(name = "unit_name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "abbreviation", length = 10)
    private String abbreviation;

}
