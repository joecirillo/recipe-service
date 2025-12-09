package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.mapper.IngredientMapper;
import com.foodiesfinds.recipe_service.repository.IngredientRepository;
import com.foodiesfinds.recipe_service.service.IngredientService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

  private final IngredientMapper ingredientMapper;

  private final IngredientRepository ingredientRepository;

  @Override
  public IngredientDTO getIngredientById(Long ingredientId) {
    return ingredientMapper.toDTO(ingredientRepository.findById(ingredientId).get());
  }
}
