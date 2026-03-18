package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.entity.Unit;
import com.foodiesfinds.recipe_service.repository.UnitRepository;
import com.foodiesfinds.recipe_service.service.implementation.UnitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnitServiceImplTest {

    @Mock
    UnitRepository unitRepository;

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
}
