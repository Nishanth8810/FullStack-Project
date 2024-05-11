package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.modal.userEntities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findByUser(User user);
}
