package com.microservices.mealplanservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.microservices.mealplanservice.modal.Connection;

import java.util.List;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {
    List<Connection> findByTrainerId(Long id);

    Connection findByTrainerIdAndUserId(Long trainerId, Long userId);
}
