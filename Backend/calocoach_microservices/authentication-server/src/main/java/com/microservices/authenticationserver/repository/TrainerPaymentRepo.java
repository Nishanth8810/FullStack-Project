package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.modal.trainerEntity.TrainerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerPaymentRepo extends JpaRepository<TrainerPayment, Long> {
    TrainerPayment findByTrainerId(User user);
}
