package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.dto.tag.TagResponseDTO;
import com.foodiesfinds.recipe_service.entity.Tag;
import com.foodiesfinds.recipe_service.mapper.TagMapper;
import com.foodiesfinds.recipe_service.repository.TagRepository;
import com.foodiesfinds.recipe_service.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    @Transactional(readOnly = true)
    public Tag resolveTag(Tag requestedTag) {
        if (!isTagValid(requestedTag)) {
            throw new BadRequestException("Tag request must have either an ID or a name.");
        }
        if (requestedTag.getId() != null) {
            return tagRepository.findById(requestedTag.getId())
                    .orElseThrow(() -> new NotFoundException("Tag ID not found: " + requestedTag.getId()));
        }
        return tagRepository.findByNameIgnoreCase(requestedTag.getName())
                .orElseGet(() -> createNewTag(requestedTag));
    }

    private boolean isTagValid(Tag tag) {
        return tag != null && (tag.getName() != null || !tag.getName().isEmpty());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponseDTO> list() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NamedEntityDTO> search(String query) {
        return tagRepository.findByNameContainingIgnoreCase(query).stream()
                .map(tag -> new NamedEntityDTO(tag.getId(), tag.getName()))
                .toList();
    }

    @Transactional
    private Tag createNewTag(Tag tag) {
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        return tagRepository.save(newTag);
    }
}
