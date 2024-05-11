package com.microservices.authenticationserver.repository;

import com.microservices.authenticationserver.modal.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Page<User> findByRoleRoleName(String RoleName, Pageable pageable);

    List<User> findByRoleRoleName(String RoleName);

    Optional<User> findByEmailIgnoreCase(String email);
}
