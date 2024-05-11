package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.OtpEntity;
import com.microservices.authenticationserver.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<OtpEntity, Long> {

    OtpEntity findByUser(User email);
}
