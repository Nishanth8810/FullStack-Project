package com.microservices.authenticationserver.service.ServiceInterfaces;


import com.microservices.authenticationserver.dto.PlanDTO.PlanReq;
import com.microservices.authenticationserver.dto.PlanDTO.PlanResponse;

import java.util.List;

public interface PlanServiceInterface {
    int save(PlanReq req);

    List<PlanResponse> getAllPlans();

    int editPlan(PlanReq req);

    int deletePlan(Long id);

    int listPlan(long id);

    int unListPlan(long id);

    List<PlanResponse> getAllPlanForTrainer();
}
