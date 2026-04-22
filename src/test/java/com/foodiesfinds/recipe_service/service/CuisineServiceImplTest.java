package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;
import com.foodiesfinds.recipe_service.mapper.CuisineMapper;
import com.foodiesfinds.recipe_service.repository.CuisineRepository;
import com.foodiesfinds.recipe_service.service.implementation.CuisineServiceIml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CuisineServiceImplTest {

    @Mock
    CuisineRepository cuisineRepository;

    @Mock
    CuisineMapper cuisineMapper;

    @InjectMocks
    CuisineServiceIml cuisineService;

    @Test
    void resolveCuisine_foundById() {
        Cuisine cuisine = new Cuisine((short) 1, "Italian");
        when(cuisineRepository.findById(1L)).thenReturn(Optional.of(cuisine));

        Cuisine result = cuisineService.resolveCuisine(cuisine);

        assertThat(result).isEqualTo(cuisine);
        verify(cuisineRepository).findById(1L);
    }

    @Test
    void resolveCuisine_notFoundById() {
        Cuisine cuisine = new Cuisine((short) 99, "Unknown");
        when(cuisineRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cuisineService.resolveCuisine(cuisine))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void resolveCuisine_foundByName() {
        Cuisine requested = new Cuisine(null, "Italian");
        Cuisine existing = new Cuisine((short) 1, "Italian");
        when(cuisineRepository.findByNameIgnoreCase("Italian")).thenReturn(Optional.of(existing));

        Cuisine result = cuisineService.resolveCuisine(requested);

        assertThat(result).isEqualTo(existing);
        verify(cuisineRepository).findByNameIgnoreCase("Italian");
    }

    @Test
    void resolveCuisine_createsNew_whenNameNotFound() {
        Cuisine requested = new Cuisine(null, "Peruvian");
        Cuisine saved = new Cuisine((short) 5, "Peruvian");
        when(cuisineRepository.findByNameIgnoreCase("Peruvian")).thenReturn(Optional.empty());
        when(cuisineRepository.save(any())).thenReturn(saved);

        Cuisine result = cuisineService.resolveCuisine(requested);

        assertThat(result).isEqualTo(saved);
        verify(cuisineRepository).save(any());
    }

    @Test
    void resolveCuisine_nullIdAndNullName_throwsBadRequest() {
        Cuisine invalid = new Cuisine(null, null);

        assertThatThrownBy(() -> cuisineService.resolveCuisine(invalid))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void resolveCuisine_nullIdAndBlankName_throwsBadRequest() {
        Cuisine invalid = new Cuisine(null, "   ");

        assertThatThrownBy(() -> cuisineService.resolveCuisine(invalid))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void list_returnsAllCuisines() {
        Cuisine cuisine = new Cuisine((short) 1, "Italian");
        CuisineResponseDTO dto = new CuisineResponseDTO();
        when(cuisineRepository.findAll()).thenReturn(List.of(cuisine));
        when(cuisineMapper.toDTO(cuisine)).thenReturn(dto);

        List<CuisineResponseDTO> result = cuisineService.list();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }

    @Test
    void search_returnsMatchingCuisines() {
        Cuisine cuisine = new Cuisine((short) 2, "Italian");
        when(cuisineRepository.findByNameContainingIgnoreCase("ital")).thenReturn(List.of(cuisine));

        List<NamedEntityDTO> result = cuisineService.search("ital");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(2L);
        assertThat(result.get(0).getName()).isEqualTo("Italian");
    }
}
