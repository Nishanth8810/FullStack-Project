package com.microservices.authenticationserver.dto.TrainerDataDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainerResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private int active;
    private Long professionalInformationId;
    private String certification;
    private String specializations;
    private int yearsOfExperience;
    private String previousEmployment;
    private String education;
    private String schedule;
    private String preferredCommunication;
    private String description;
    private String planName;
    private double Amount;
    private String memberSince;
    private String imageUrl;

}
