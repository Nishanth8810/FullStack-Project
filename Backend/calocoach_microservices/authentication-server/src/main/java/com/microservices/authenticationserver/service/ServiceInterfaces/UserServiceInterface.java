package com.microservices.authenticationserver.service.ServiceInterfaces;

import com.microservices.authenticationserver.dto.*;
import com.microservices.authenticationserver.modal.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {
    User findUserByEmail(String email);

    UserResponse createdUser(UserRegisterReq req, String roles) throws Exception;

    boolean existsByEmail(String email);

    void updateUser(Long userId, User updatedUser);

    ResponseEntity<JwtResponse> verify(OtpReq req);

    HttpStatus verifyEmail(String email) throws Exception;

    HttpStatus changePass(ChangePassReq changePassReq);

    List<UserResponse> findAllUsers();

    HttpStatus unblock(String userId);

    HttpStatus block(String userId);

    HttpStatus resendOTP(String email);

    HttpStatus isBlocked(String email);

    Page<UserResponse> findAll(Pageable pageable);

    ResponseEntity<UserResponse> findUserById(Long id);

}
