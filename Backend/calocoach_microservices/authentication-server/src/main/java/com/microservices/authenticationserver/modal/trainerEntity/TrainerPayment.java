package com.microservices.authenticationserver.modal.trainerEntity;

import com.microservices.authenticationserver.modal.Plans.Plan;
import com.microservices.authenticationserver.modal.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    private User trainer;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    private double paymentAmount;
    private LocalDateTime paymentDate;


}
