package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.entity.Unit;

import java.util.List;

public interface UnitService {

    Unit resolveUnit(Unit unit);

    List<NamedEntityDTO> list();

}
