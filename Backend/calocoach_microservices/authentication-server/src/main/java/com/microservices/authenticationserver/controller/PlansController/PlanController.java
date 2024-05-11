package com.microservices.authenticationserver.controller.PlansController;

import com.microservices.authenticationserver.dto.PlanDTO.PlanReq;
import com.microservices.authenticationserver.dto.PlanDTO.PlanResponse;
import com.microservices.authenticationserver.service.planService.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;


    @PostMapping("/save")
    public int savePlans(@RequestBody PlanReq req) {
        try {
            return planService.save(req);
        } catch (Exception e) {
            log.error("Exception in savePlans in PlanController ", e);
            return 500;
        }
    }

    @GetMapping("/getAllPlan")
    public List<PlanResponse> getAllPlan() {
        try {
            return planService.getAllPlans();
        } catch (Exception e) {
            log.error("Exception in editPlan in PlanController ", e);
            return new ArrayList<>();
        }
    }

    @PostMapping("/edit")
    public int editPlan(@RequestBody PlanReq req) {
        try {
            return planService.editPlan(req);
        } catch (Exception e) {
            log.error("Exception in editPlan in PlanController ", e);
            return 500;
        }
    }

    @PutMapping("/plan/list/{id}")
    public int listPlan(@PathVariable long id) {
        try {
            return planService.listPlan(id);
        } catch (Exception e) {
            log.error("Exception in listPlan in PlanController ", e);
            return 500;
        }
    }

    @PutMapping("/plan/unList/{id}")
    public int unListPlan(@PathVariable long id) {
        try {
            return planService.unListPlan(id);
        } catch (Exception e) {
            log.error("Exception in unListPlan in PlanController ", e);
            return 500;
        }

    }

    @DeleteMapping("/delete/{id}")
    public int editPlan(@PathVariable Long id) {
        try {
            return planService.deletePlan(id);
        } catch (Exception e) {
            log.error("Exception in editPlan in PlanController ", e);
            return 500;
        }
    }

}
