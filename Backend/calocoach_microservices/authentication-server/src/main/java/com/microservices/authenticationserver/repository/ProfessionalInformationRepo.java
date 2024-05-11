package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.modal.trainerEntity.ProfessionalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessionalInformationRepo extends JpaRepository<ProfessionalInformation, Long> {
    ProfessionalInformation findByUser(User user);
}
