package com.foodiesfinds.recipe_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitDTO {

  @NotNull(message = "Unit ID is required")
  private Long id;
  private String name;
  private String abbreviation;

}
