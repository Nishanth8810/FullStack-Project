package com.microservices.authenticationserver.modal.userEntities;

import com.microservices.authenticationserver.modal.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CalorieTracker {
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date date;
    private double calorie;
}
