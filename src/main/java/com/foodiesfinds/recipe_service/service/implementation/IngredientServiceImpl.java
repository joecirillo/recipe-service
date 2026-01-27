package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.mapper.IngredientMapper;
import com.foodiesfinds.recipe_service.repository.IngredientRepository;
import com.foodiesfinds.recipe_service.service.IngredientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

  private final IngredientMapper ingredientMapper;

  private final IngredientRepository ingredientRepository;

  @Override
  public IngredientDTO getIngredientById(Long ingredientId) {
    return ingredientMapper.toDTO(ingredientRepository.findById(ingredientId).get());
  }
}
