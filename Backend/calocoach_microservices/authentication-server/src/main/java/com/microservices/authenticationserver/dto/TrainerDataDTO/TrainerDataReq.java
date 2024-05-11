package com.microservices.authenticationserver.dto.TrainerDataDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TrainerDataReq {
    @Schema(description = "Trainer's certification", example = "ACE Certified Personal Trainer")
    private String certification;
    @Schema(description = "Trainer's specializations", example = "Weight training, Yoga")
    private String specializations;
    @Schema(description = "Trainer's years of experience", example = "5")
    private int yearsOfExperience;
    @Schema(description = "Trainer's previous employment", example = "Fitness First")
    private String previousEmployment;
    @Schema(description = "Trainer's education background", example = "Bachelor's in Exercise Science")
    private String education;
    @Schema(description = "Trainer's schedule availability", example = "Mon-Fri 8am-12pm")
    private String schedule;
    @Schema(description = "Trainer's preferred communication method", example = "Email")
    private String preferredCommunication;
    @Schema(description = "Brief description of the trainer", example = "Passionate about helping clients reach their fitness goals")
    private String description;
    @Schema(description = "Trainer's email address", example = "trainer@example.com")
    private String userEmail;

}
