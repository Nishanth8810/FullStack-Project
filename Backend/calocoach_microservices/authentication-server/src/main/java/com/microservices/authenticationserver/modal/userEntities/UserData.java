package com.microservices.authenticationserver.modal.userEntities;

import com.microservices.authenticationserver.modal.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_data_id")
    private List<Exclusions> exclusions;
    private double weight;
    private double height;
    private String gender;
    private int age;
    private double bodyFat;
    private String activityLevel;
    private String dietType;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
