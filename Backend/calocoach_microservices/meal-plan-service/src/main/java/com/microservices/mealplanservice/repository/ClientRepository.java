package com.microservices.mealplanservice.repository;

import com.microservices.mealplanservice.modal.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.mealplanservice.modal.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByTrainerIdAndUserId(Long trainerId, Long clientId);

    List<Client> findByTrainerId(long id);

    Client findByUserId(long id);
}
