package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.ingredient.IngredientResponseDTO;
import com.foodiesfinds.recipe_service.entity.Ingredient;
import com.foodiesfinds.recipe_service.mapper.IngredientMapper;
import com.foodiesfinds.recipe_service.repository.IngredientRepository;
import com.foodiesfinds.recipe_service.service.implementation.IngredientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    IngredientMapper ingredientMapper;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Test
    void getIngredientById_found() {
        Ingredient ingredient = new Ingredient(1L, "flour");
        IngredientResponseDTO dto = new IngredientResponseDTO();
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.toDTO(ingredient)).thenReturn(dto);

        IngredientResponseDTO result = ingredientService.getIngredientById(1L);

        assertThat(result).isEqualTo(dto);
    }

    @Test
    void resolveIngredient_foundById() {
        Ingredient requested = new Ingredient(3L, "salt");
        Ingredient found = new Ingredient(3L, "salt");
        when(ingredientRepository.findById(3L)).thenReturn(Optional.of(found));

        Ingredient result = ingredientService.resolveIngredient(requested);

        assertThat(result).isEqualTo(found);
        verify(ingredientRepository).findById(3L);
    }

    @Test
    void resolveIngredient_notFoundById() {
        Ingredient requested = new Ingredient(99L, "ghost");
        when(ingredientRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ingredientService.resolveIngredient(requested))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void resolveIngredient_foundByName() {
        Ingredient requested = new Ingredient(null, "basil");
        Ingredient existing = new Ingredient(4L, "basil");
        when(ingredientRepository.findByNameIgnoreCase("basil")).thenReturn(Optional.of(existing));

        Ingredient result = ingredientService.resolveIngredient(requested);

        assertThat(result).isEqualTo(existing);
        verify(ingredientRepository).findByNameIgnoreCase("basil");
    }

    @Test
    void resolveIngredient_createsNew_whenNameNotFound() {
        Ingredient requested = new Ingredient(null, "truffle");
        Ingredient saved = new Ingredient(10L, "truffle");
        when(ingredientRepository.findByNameIgnoreCase("truffle")).thenReturn(Optional.empty());
        when(ingredientRepository.save(any())).thenReturn(saved);

        Ingredient result = ingredientService.resolveIngredient(requested);

        assertThat(result).isEqualTo(saved);
        verify(ingredientRepository).save(any());
    }

    @Test
    void resolveIngredient_nullIdAndNullName_throwsBadRequest() {
        Ingredient invalid = new Ingredient(null, null);

        assertThatThrownBy(() -> ingredientService.resolveIngredient(invalid))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void resolveIngredient_nullIdAndBlankName_throwsBadRequest() {
        Ingredient invalid = new Ingredient(null, "  ");

        assertThatThrownBy(() -> ingredientService.resolveIngredient(invalid))
                .isInstanceOf(BadRequestException.class);
    }
}
