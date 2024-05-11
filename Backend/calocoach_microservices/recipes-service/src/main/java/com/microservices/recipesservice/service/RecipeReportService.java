package com.microservices.recipesservice.service;

import com.microservices.recipesservice.dto.ReportRecipeReqDTO;
import com.microservices.recipesservice.modal.Recipe;
import com.microservices.recipesservice.modal.ReportedRecipe;
import com.microservices.recipesservice.repository.RecipeRepository;
import com.microservices.recipesservice.repository.ReportedRecipeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeReportService {

    private final ReportedRecipeRepo reportedRecipeRepo;
    private final RecipeRepository recipeRepository;

    public HttpStatus saveReport(ReportRecipeReqDTO recipeReqDTO) {
        try {
            saveRecipeReport(recipeReqDTO);
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private void saveRecipeReport(ReportRecipeReqDTO recipeReqDTO) {
        Recipe recipe = recipeRepository.findById(recipeReqDTO.getRecipeId()).orElseThrow();
        ReportedRecipe reportedRecipe = new ReportedRecipe();
        reportedRecipe.setRecipe(recipe);
        reportedRecipe.setTotalReports(reportedRecipe.getTotalReports() + 1);
        reportedRecipe.setUserEmail(recipeReqDTO.getUserEmail());
        reportedRecipe.setReason(recipeReqDTO.getReason());
        reportedRecipeRepo.save(reportedRecipe);
    }
}
