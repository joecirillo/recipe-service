package com.foodiesfinds.recipe_service.service.implementation;

import com.foodiesfinds.recipe_service.core.exception.BadRequestException;
import com.foodiesfinds.recipe_service.core.exception.NotFoundException;
import com.foodiesfinds.recipe_service.dto.CuisineResponseDTO;
import com.foodiesfinds.recipe_service.dto.NamedEntityDTO;
import com.foodiesfinds.recipe_service.entity.Cuisine;
import com.foodiesfinds.recipe_service.mapper.CuisineMapper;
import com.foodiesfinds.recipe_service.repository.CuisineRepository;
import com.foodiesfinds.recipe_service.service.CuisineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CuisineServiceIml implements CuisineService {

    private final CuisineRepository cuisineRepository;
    private final CuisineMapper cuisineMapper;

    @Override
    @Transactional(readOnly = true)
    public Cuisine resolveCuisine(Cuisine requestedCuisine) {
        if (!isCuisineValid(requestedCuisine)) {
            throw new BadRequestException("Cuisine request must have either an ID or a name.");
        }
        if (requestedCuisine.getId() != null) {
            return cuisineRepository.findById(Long.valueOf(requestedCuisine.getId()))
                    .orElseThrow(() -> new NotFoundException("Cuisine ID not found: "
                            + requestedCuisine.getId()));
        }
        return cuisineRepository.findByNameIgnoreCase(requestedCuisine.getName())
                .orElseGet(() -> createNewCuisine(requestedCuisine));
    }

    private boolean isCuisineValid(Cuisine req) {
        return req.getId() != null || (req.getName() != null && !req.getName().isBlank());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuisineResponseDTO> list() {
        return cuisineRepository.findAll().stream()
                .map(cuisineMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NamedEntityDTO> search(String query) {
        return cuisineRepository.findByNameContainingIgnoreCase(query).stream()
                .map(c -> new NamedEntityDTO(Long.valueOf(c.getId()), c.getName()))
                .toList();
    }

    @Transactional
    private Cuisine createNewCuisine(Cuisine cuisine) {
        Cuisine newCuisine = new Cuisine();
        newCuisine.setName(cuisine.getName());
        return cuisineRepository.save(newCuisine);
    }
}
