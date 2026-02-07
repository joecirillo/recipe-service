package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.common.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.IngredientDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.mapper.IngredientMapper;
import com.foodiesfinds.recipe_service.repository.IngredientRepository;
import com.foodiesfinds.recipe_service.service.IngredientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.foodiesfinds.recipe_service.common.exception.BadRequestException;
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

  @Override
  public Ingredient resolveIngredient(Ingredient requestedIngredient) {
    if (!isIngredientValid(requestedIngredient)) {
      throw new BadRequestException("Ingredient request must have either an ID or a name.");
    }
    if (requestedIngredient.getId() != null) {
      return ingredientRepository.findById(requestedIngredient.getId())
          .orElseThrow(() -> new NotFoundException("Ingredient ID not found: " + requestedIngredient.getId()));
    }
    return ingredientRepository.findByNameIgnoreCase(requestedIngredient.getName())
        .orElseGet(() -> createNewIngredient(requestedIngredient.getName()));
  }

  private boolean isIngredientValid(Ingredient req) {
    return req.getId() != null || (req.getName() != null && !req.getName().isBlank());
  }

  private Ingredient createNewIngredient(String name) {
    Ingredient newIngredient = new Ingredient();
    newIngredient.setName(name);
    return ingredientRepository.save(newIngredient);
  }
}
