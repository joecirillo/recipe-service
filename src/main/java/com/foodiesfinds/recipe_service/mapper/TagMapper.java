package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.tag.TagResponseDTO;
import com.foodiesfinds.recipe_service.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagResponseDTO tagDTO);

    TagResponseDTO toDTO(Tag tag);

}
