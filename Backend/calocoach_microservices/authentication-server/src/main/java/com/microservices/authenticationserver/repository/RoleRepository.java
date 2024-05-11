package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
