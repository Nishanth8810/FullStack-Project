package com.microservices.mealplanservice.controller;

import com.microservices.mealplanservice.dto.mealDto.MealPlanDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microservices.mealplanservice.dto.mealDto.MealPlanDTO;
import com.microservices.mealplanservice.dto.mealDto.MealPlanRequestDTO;
import com.microservices.mealplanservice.dto.mealDto.MealPlanResponse;
import com.microservices.mealplanservice.service.MealPlanService;

import java.util.List;

@RestController
@RequestMapping("/meal-plan")
@RequiredArgsConstructor
@Slf4j
public class MealPlanController {
    private final MealPlanService mealPlanService;

    @PostMapping("/create")
    public ResponseEntity<MealPlanDTO> createMealPlan(
            @RequestBody MealPlanRequestDTO mealPlanRequestDTO
    ) {
        try {
            MealPlanDTO mealPlan = mealPlanService.createMealPlan(mealPlanRequestDTO);
            return ResponseEntity.ok(mealPlan);
        } catch (Exception e) {
            log.error("Exception in createMealPlan in mealPlanController: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    ///get plans by user id
    @GetMapping("/mealPlan/{id}")
    public ResponseEntity<List<MealPlanDTO>> getMealPlan(@PathVariable long id) {
        try {
            List<MealPlanDTO> mealPlan = mealPlanService.getMealPlan(id);
            return ResponseEntity.ok(mealPlan);
        } catch (Exception e) {
            log.error("Exception in getMealPlan in mealPlanController: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // get plan by plan id
    @GetMapping("/getPlanById/{id}")
    public ResponseEntity<MealPlanDTO> getMealPlanByPlanId(@PathVariable long id) {
        try {
            MealPlanDTO mealPlanByPlanId = mealPlanService.getMealPlanByPlanId(id);
            return ResponseEntity.ok(mealPlanByPlanId);
        } catch (Exception e) {
            log.error("Exception in getMealPlanByPlanId in mealPlanController: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePlanById/{id}")
    public HttpStatus deleteMealPlanByPlanId(@PathVariable long id) {
        try {
            return mealPlanService.deleteMealPlanByPlanId(id);
        } catch (Exception e) {
            log.error("Exception in deleteMealPlanByPlanId in mealPlanController: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    //get plans by trainerId
    @GetMapping("/allMealPlan/{id}")
    public ResponseEntity<List<MealPlanResponse>> getMealPlans(@PathVariable long id) {
        try {
            return mealPlanService.getAllPlans(id);
        } catch (Exception e) {
            log.error("Exception in getMealPlans in mealPlanController: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
