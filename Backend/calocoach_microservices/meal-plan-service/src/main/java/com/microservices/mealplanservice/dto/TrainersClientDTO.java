package com.microservices.mealplanservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainersClientDTO {
    private String firstname;
    private String lastname;
    private String email;
    private int active;
    private String profilePic;
    private long userId;
}
