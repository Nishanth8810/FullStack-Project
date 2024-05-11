package com.microservices.authenticationserver.service.planService;

import com.microservices.authenticationserver.dto.PlanDTO.PlanReq;
import com.microservices.authenticationserver.dto.PlanDTO.PlanResponse;
import com.microservices.authenticationserver.modal.Plans.Plan;
import com.microservices.authenticationserver.repository.PlanRepository;
import com.microservices.authenticationserver.service.ServiceInterfaces.PlanServiceInterface;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService implements PlanServiceInterface {
    @Autowired
    PlanRepository planRepository;

    @Override
    public int save(PlanReq req) {
        try {
            Plan plan = new Plan();
            savePlan(req, plan);
            return HttpStatus.SC_CREATED;
        } catch (Exception e) {
            return HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }
    }

    private void savePlan(PlanReq req, Plan plan) {
        plan.setPlanName(req.getPlanName());
        plan.setAmountCollaborate(req.getAmountCollaborate());
        plan.setAmountControl(req.getAmountControl());
        plan.setNumberOfClients(req.getNumberOfClients());
        plan.setDescription(req.getDescription());
        plan.setCreatedTime(LocalDateTime.now());
        plan.setList(1);
        planRepository.save(plan);
    }

    @Override
    public List<PlanResponse> getAllPlans() {
        List<Plan> plans = planRepository.findAll();
        List<PlanResponse> planResponses = new ArrayList<>();

        for (Plan plan : plans) {
            planResponses.add(toDto(plan));
        }
        return planResponses;
    }


    @Override
    public int editPlan(PlanReq req) {
        try {
            Plan plan = planRepository.findById(req.getId()).orElseThrow();
            savePlan(req, plan);
            return HttpStatus.SC_ACCEPTED;
        } catch (Exception e) {
            return HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }

    }

    @Override
    public int deletePlan(Long id) {
        try {
            planRepository.deleteById(id);
            return HttpStatus.SC_OK;
        } catch (Exception e) {
            return HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public int listPlan(long id) {
        Plan plan = planRepository.findById(id).orElseThrow();
        plan.setList(1);
        planRepository.save(plan);
        return HttpStatus.SC_OK;
    }

    @Override
    public int unListPlan(long id) {
        Plan plan = planRepository.findById(id).orElseThrow();
        plan.setList(0);
        planRepository.save(plan);
        return HttpStatus.SC_OK;
    }

    @Override
    public List<PlanResponse> getAllPlanForTrainer() {
        List<Plan> plans = planRepository.findAll();
        List<PlanResponse> planResponses = new ArrayList<>();

        for (Plan plan : plans) {
            if (plan.getList() == 1) {
                planResponses.add(toDto(plan));
            }
        }
        return planResponses;
    }

    private PlanResponse toDto(Plan plan) {
        PlanResponse planResponse = new PlanResponse();
        planResponse.setId(plan.getId());
        planResponse.setAmountControl(plan.getAmountControl());
        planResponse.setAmountCollaborate(plan.getAmountCollaborate());
        planResponse.setNumberOfClients(plan.getNumberOfClients());
        planResponse.setPlanName(plan.getPlanName());
        planResponse.setCreatedTime(plan.getCreatedTime());
        planResponse.setDescription(plan.getDescription());
        planResponse.setList(plan.getList());
        return planResponse;
    }

}

