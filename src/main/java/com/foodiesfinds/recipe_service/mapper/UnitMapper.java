package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.UnitResponseDTO;
import com.foodiesfinds.recipe_service.entity.Unit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    Unit toEntity(UnitResponseDTO unitDTO);

    UnitResponseDTO toDTO(Unit unit);

}
