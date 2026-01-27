package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.UnitDTO;
import com.foodiesfinds.recipe_service.entity.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UnitMapper {

  Unit toEntity(UnitDTO unitDTO);

  UnitDTO toDTO(Unit unit);

}
