package com.microservices.authenticationserver.modal.trainerEntity;

import com.microservices.authenticationserver.modal.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class ProfessionalInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String certification;
    private String specializations;
    private int yearsOfExperience;
    private String previousEmployment;
    private String education;
    private String schedule;
    private String preferredCommunication;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String description;
    private String profilePic;
    private int paymentCompleted;
}