package com.microservices.authenticationserver.modal.Plans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amountControl;
    private double amountCollaborate;
    private String planName;
    private int numberOfClients;
    private LocalDateTime createdTime;
    private String description;
    private int list;

}
