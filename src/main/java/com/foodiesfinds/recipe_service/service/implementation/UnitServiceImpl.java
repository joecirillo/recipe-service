package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.entity.Unit;
import com.foodiesfinds.recipe_service.repository.UnitRepository;
import com.foodiesfinds.recipe_service.service.UnitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public Unit resolveUnit(Unit unit) {
        return unitRepository.findById(Long.valueOf(unit.getId()))
                .orElseThrow(() -> new NotFoundException("Unit ID not found: "
                        + unit.getId()));
    }

}
