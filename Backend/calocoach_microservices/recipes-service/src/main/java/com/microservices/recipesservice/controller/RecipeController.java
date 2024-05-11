package com.microservices.recipesservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.recipesservice.dto.RecipeRequestDTO;
import com.microservices.recipesservice.dto.RecipeResponse;
import com.microservices.recipesservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/save")
    public HttpStatus saveRecipe(@RequestParam("recipeDTO") String recipeDTOJson,
                                 @RequestParam("imageFile") MultipartFile imageFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        RecipeRequestDTO recipeDTO = null;
        try {
            recipeDTO = objectMapper.readValue(recipeDTOJson, RecipeRequestDTO.class);
        } catch (JsonProcessingException e) {
            log.error("jsonProcessing Exception in RecipeController", e);
            return HttpStatus.BAD_REQUEST;
        }
        boolean saved = recipeService.saveRecipe(recipeDTO, imageFile);
        if (saved) {
            log.info("saved recipe in RecipeController");
            return HttpStatus.CREATED;
        } else {
            log.error("Exception in RecipeController");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/allRecipes")
    public List<RecipeResponse> getAllRecipes() {
        try {
            return recipeService.findAll();
        } catch (Exception e) {
            log.error("Exception getAllRecipes method in RecipeController", e);
            return new ArrayList<>();
        }

    }

    @GetMapping("/allRecipesPaginated")
    public Page<RecipeResponse> getAllRecipesPaginated(@RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return recipeService.findAllPaginated(pageable);
        } catch (Exception e) {
            log.error("Exception getAllRecipes method in RecipeController", e);
            throw new RuntimeException();
        }

    }


    @PutMapping("/un-list/{recipeId}")
    public HttpStatus unListRecipes(@PathVariable("recipeId") long id) {
        try {
            return recipeService.unlist(id);
        } catch (Exception e) {
            log.error("Exception unListRecipes method in RecipeController", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PutMapping("/list/{recipeId}")
    public HttpStatus ListRecipes(@PathVariable("recipeId") long id) {
        try {
            return recipeService.list(id);
        } catch (Exception e) {
            log.error("Exception ListRecipes method in RecipeController", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @PostMapping("/edit")
    public HttpStatus editRecipe(@RequestParam("recipeDTO") String recipeDTOJson,
                                 @RequestParam("imageFile") MultipartFile imageFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        RecipeRequestDTO recipeDTO;
        try {
            recipeDTO = objectMapper.readValue(recipeDTOJson, RecipeRequestDTO.class);
        } catch (JsonProcessingException e) {
            log.error("jsonProcessing Exception in RecipeController", e);
            return HttpStatus.BAD_REQUEST;
        }
        boolean saved = recipeService.editRecipe(recipeDTO, imageFile);
        if (saved) {
            log.info("edited recipe in RecipeController");
            return HttpStatus.CREATED;
        } else {
            log.info("Exception edit recipe in RecipeController");
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/getRecipeByCalorieBetween")
    public ResponseEntity<List<RecipeResponse>> getRecipes(
            @RequestParam("start") long start,
            @RequestParam("end") long end) {
        try {
            var responses = recipeService.getByCalorie(start, end);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            log.error("Exception getRecipes method in RecipeController", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allRecipe")
    public ResponseEntity<List<RecipeResponse>> getAllRecipes(
            @RequestParam("searchQuery") String searchQuery) {
        try {
            List<RecipeResponse> recipeResponses = recipeService
                    .findAllByContaining(searchQuery);
            return ResponseEntity.ok(recipeResponses);
        } catch (Exception e) {
            log.error("Exception getAllRecipes method in RecipeController", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}