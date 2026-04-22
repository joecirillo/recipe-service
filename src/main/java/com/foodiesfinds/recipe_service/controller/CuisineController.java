package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.service.CuisineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/cuisine")
@RequiredArgsConstructor
public class CuisineController {

    private final CuisineService cuisineService;
    private final ResponseFactory response;

    @GetMapping("/list")
    public ResponseEntity<Response> getCuisines() {
        return response.buildResponse(OK, "Cuisines retrieved", cuisineService.list());
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchCuisines(@RequestParam("query") String query) {
        return response.buildResponse(OK, "Cuisines queried", cuisineService.search(query));
    }

}
