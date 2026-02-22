package com.foodiesfinds.recipe_service.entity;

import jakarta.persistence.*;
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
    Short id;

    @Column(name = "cuisine", length = 100, nullable = false, unique = true)
    String name;

}
