package com.foodiesfinds.recipe_service.service;

import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.entity.Tag;
import com.foodiesfinds.recipe_service.repository.TagRepository;
import com.foodiesfinds.recipe_service.service.implementation.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    TagRepository tagRepository;

    @InjectMocks
    TagServiceImpl tagService;

    @Test
    void resolveTag_foundById() {
        Tag requested = new Tag(10L, "dinner");
        when(tagRepository.findById(10L)).thenReturn(Optional.of(requested));

        Tag result = tagService.resolveTag(requested);

        assertThat(result).isEqualTo(requested);
        verify(tagRepository).findById(10L);
    }

    @Test
    void resolveTag_notFoundById() {
        Tag requested = new Tag(99L, "missing");
        when(tagRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.resolveTag(requested))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void resolveTag_foundByName() {
        Tag requested = new Tag(null, "vegan");
        Tag existing = new Tag(5L, "vegan");
        when(tagRepository.findByNameIgnoreCase("vegan")).thenReturn(Optional.of(existing));

        Tag result = tagService.resolveTag(requested);

        assertThat(result).isEqualTo(existing);
        verify(tagRepository).findByNameIgnoreCase("vegan");
    }

    @Test
    void resolveTag_createsNew_whenNameNotFound() {
        Tag requested = new Tag(null, "keto");
        Tag saved = new Tag(7L, "keto");
        when(tagRepository.findByNameIgnoreCase("keto")).thenReturn(Optional.empty());
        when(tagRepository.save(any())).thenReturn(saved);

        Tag result = tagService.resolveTag(requested);

        assertThat(result).isEqualTo(saved);
        verify(tagRepository).save(any());
    }

    @Test
    void resolveTag_nullTag_throwsBadRequest() {
        assertThatThrownBy(() -> tagService.resolveTag(null))
                .isInstanceOf(BadRequestException.class);
    }
}
