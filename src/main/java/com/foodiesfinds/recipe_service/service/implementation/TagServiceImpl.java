package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.entity.Tag;
import com.foodiesfinds.recipe_service.repository.TagRepository;
import com.foodiesfinds.recipe_service.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

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

    @Transactional
    private Tag createNewTag(Tag tag) {
        Tag newTag = new Tag();
        newTag.setName(tag.getName());
        return tagRepository.save(newTag);
    }
}
