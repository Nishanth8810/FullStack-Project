package com.microservices.authenticationserver.dto.UserDataDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDataResponse {
    private List<String> exclusions;
    private String preDiet;
    private String goal;
    private double weight;
    private double height;
    private String gender;
    private int age;
    private double bodyFat;
    private String activityLevel;
    private String userEmail;
    private String firstname;
    private String lastname;
    private String password;
    private String profilePic;

}
