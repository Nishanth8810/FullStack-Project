package com.microservices.recipesservice.controller;

import com.microservices.recipesservice.dto.RecipeResponse;
import com.microservices.recipesservice.dto.ReportRecipeReqDTO;
import com.microservices.recipesservice.dto.SavedRecipeDTO;
import com.microservices.recipesservice.service.RecipeReportService;
import com.microservices.recipesservice.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeActionController {
    private final RecipeReportService recipeReportService;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/food/{recipeId}")
    public ResponseEntity<RecipeResponse> SingleRecipeResponse
            (@PathVariable long recipeId) {
        try {
            return recipeService.getById(recipeId);
        } catch (Exception e) {
            log.error("Exception occurred in SingleRecipeResponse method in RecipeActionController", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/food/delete/{recipeId}")
    public HttpStatus deleteRecipe(@PathVariable long recipeId) {
        try {
            return recipeService.deleteById(recipeId);
        } catch (Exception e) {
            log.error("Exception occurred in deleteRecipe method in RecipeActionController", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @PostMapping("/recipeReport")
    public HttpStatus reportRecipe(@RequestBody ReportRecipeReqDTO recipeReqDTO) {
        try {
            return recipeReportService.saveReport(recipeReqDTO);
        } catch (Exception e) {
            log.error("Exception occurred in reportRecipe method in RecipeActionController", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @PostMapping("/saveRecipe")
    public HttpStatus saveRecipe(@RequestBody SavedRecipeDTO recipeSaveDTO) {
        try {
            return recipeService.saveRecipeAndUser(recipeSaveDTO);
        } catch (Exception e) {
            log.error("Exception occurred in saveRecipe method in RecipeActionController", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @DeleteMapping("/deleteSavedRecipe/{id}")
    public HttpStatus deleteRecipe(@PathVariable Long id) {
        try {
            return recipeService.deleteRecipe(id);
        } catch (Exception e) {
            log.error("Exception occurred in deleteRecipe method in RecipeActionController", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/getAllRecipeOfUser")
    public ResponseEntity<List<RecipeResponse>> getRecipeOfUser(
            @RequestParam Long userId
    ) {
        try {
            return recipeService.getUserRecipe(userId);
        } catch (Exception e) {
            log.error("Exception occurred in deleteRecipe method in RecipeActionController", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
