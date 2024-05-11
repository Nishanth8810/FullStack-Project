package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.Plans.Plan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlanRepository extends JpaRepository<Plan, Long> {
    Plan findByPlanName(String planName);
}
