package com.foodiesfinds.recipe_service.controller;

import com.foodiesfinds.recipe_service.core.response.ResponseFactory;
import com.foodiesfinds.recipe_service.dto.core.Response;
import com.foodiesfinds.recipe_service.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final ResponseFactory response;

    @GetMapping("/list")
    public ResponseEntity<Response> getUnits() {
        return response.buildResponse(OK, "Units retrieved", unitService.list());
    }

}
