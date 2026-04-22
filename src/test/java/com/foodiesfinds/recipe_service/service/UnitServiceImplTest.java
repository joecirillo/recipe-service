package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.entity.Unit;
import com.foodiesfinds.recipe_service.mapper.UnitMapper;
import com.foodiesfinds.recipe_service.repository.UnitRepository;
import com.foodiesfinds.recipe_service.service.implementation.UnitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplTest {

    @Mock
    UnitRepository unitRepository;

    @Mock
    UnitMapper unitMapper;

    @InjectMocks
    UnitServiceImpl unitService;

    @Test
    void resolveUnit_found() {
        Unit unit = new Unit(1L, "cup", "c");
        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));

        Unit result = unitService.resolveUnit(unit);

        assertThat(result).isEqualTo(unit);
        verify(unitRepository).findById(1L);
    }

    @Test
    void resolveUnit_notFound() {
        Unit unit = new Unit(99L, "cup", "c");
        when(unitRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> unitService.resolveUnit(unit))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void list_returnsAllUnits() {
        Unit unit = new Unit(1L, "cup", "c");
        NamedEntityDTO dto = new NamedEntityDTO(1L, "cup");
        when(unitRepository.findAll()).thenReturn(List.of(unit));

        List<NamedEntityDTO> result = unitService.list();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(dto);
    }
}
