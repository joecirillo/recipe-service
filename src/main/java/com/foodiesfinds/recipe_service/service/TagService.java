package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.tag.TagResponseDTO;
import com.foodiesfinds.recipe_service.entity.Tag;

import java.util.List;

public interface TagService {

    Tag resolveTag(Tag requestedTag);

    List<TagResponseDTO> list();

    List<NamedEntityDTO> search(String query);
}
