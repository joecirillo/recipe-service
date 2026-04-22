package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.entity.Unit;
import com.foodiesfinds.recipe_service.repository.UnitRepository;
import com.foodiesfinds.recipe_service.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    @Transactional(readOnly = true)
    public Unit resolveUnit(Unit unit) {
        return unitRepository.findById(Long.valueOf(unit.getId()))
                .orElseThrow(() -> new NotFoundException("Unit ID not found: "
                        + unit.getId()));
    }

    @Override
    public List<NamedEntityDTO> list() {
        return unitRepository.findAll().stream()
                .map(u -> new NamedEntityDTO(u.getId(), u.getName()))
                .toList();
    }

}
