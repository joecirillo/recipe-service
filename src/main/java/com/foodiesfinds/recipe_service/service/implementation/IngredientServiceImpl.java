package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.ingredient.IngredientResponseDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.mapper.IngredientMapper;
import com.foodiesfinds.recipe_service.repository.IngredientRepository;
import com.foodiesfinds.recipe_service.service.IngredientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

  private final IngredientMapper ingredientMapper;

  private final IngredientRepository ingredientRepository;

  @Override
  public IngredientResponseDTO getIngredientById(Long ingredientId) {
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
    log.info("Validating ingredient request: {}", req);
    return req.getId() != null || (req.getName() != null && !req.getName().isBlank());
  }

  private Ingredient createNewIngredient(String name) {
    log.info("Creating new ingredient with name: {}", name);
    Ingredient newIngredient = new Ingredient();
    newIngredient.setName(name);
    return ingredientRepository.save(newIngredient);
  }
}
