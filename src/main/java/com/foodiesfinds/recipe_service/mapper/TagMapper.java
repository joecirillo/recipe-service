package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.TagDTO;
import com.foodiesfinds.recipe_service.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {

  Tag toEntity(TagDTO tagDTO);

  TagDTO toDTO(Tag tag);

}
