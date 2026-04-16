package com.foodiesfinds.recipe_service.mapper;

import com.foodiesfinds.recipe_service.dto.tag.TagResponseDTO;
import com.foodiesfinds.recipe_service.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(source = "tagId", target = "id")
    @Mapping(source = "tagName", target = "name")
    Tag toEntity(TagResponseDTO tagDTO);

    @Mapping(source = "id", target = "tagId")
    @Mapping(source = "name", target = "tagName")
    TagResponseDTO toDTO(Tag tag);

}
