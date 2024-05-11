package com.microservices.authenticationserver.controller;

import com.microservices.authenticationserver.dto.*;
import com.microservices.authenticationserver.enums.roles;
import com.microservices.authenticationserver.jwt.JwtService;
import com.microservices.authenticationserver.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    private final JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegisterReq req) throws Exception {
        try {
            if (service.existsByEmail(req.getEmail())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            UserResponse userResponse = service.createdUser(req, roles.USER.name());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception in registerUser method in UserController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/resendOTP/{email}")
    public HttpStatus resendOTP(@PathVariable("email") String email) {
        try {
            return service.resendOTP(email);
        } catch (Exception e) {
            log.error("Exception in resendOTP method in UserController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @PostMapping("/trainer-register")
    public ResponseEntity<UserResponse> registerTrainer(@RequestBody UserRegisterReq req) {
        try {
            UserResponse userResponse = service.createdUser(req, roles.TRAINER.name());
            return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception in registerTrainer method in UserController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/unblock/{userId}")
    public HttpStatus unblock(@PathVariable("userId") String userId) {
        try {
            return service.unblock(userId);
        } catch (Exception e) {
            log.error("Exception in unblock method in UserController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @PutMapping("/block/{userId}")
    public HttpStatus block(@PathVariable("userId") String userId) {
        try {
            return service.block(userId);
        } catch (Exception e) {
            log.error("Exception in block method in UserController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserRegisterReq userRegisterReq = getUserRegisterReq(loginRequest);
            return jwtService.createJwtToken(userRegisterReq);
        } catch (Exception e) {
            log.error("Exception in method in UserController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verifyOTP")
    public ResponseEntity<JwtResponse> verifyOtp(@RequestBody OtpReq req) {
        try {
            return service.verify(req);
        } catch (Exception e) {
            log.error("Exception in verifyOtp in UserController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password")
    public HttpStatus changePassword(@RequestBody ChangePassReq changePassReq) {
        try {
            return service.changePass(changePassReq);
        } catch (Exception e) {
            log.error("Exception in  changePassword in UserController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/send-reset-email/{email}")
    public HttpStatus verifyEmail(@PathVariable String email) {
        try {
            return service.verifyEmail(email);
        } catch (Exception e) {
            log.error("Exception in verifyEmail in UserController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> findAll() {
        try {
            List<UserResponse> users = service.findAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Exception in findAllUsers in UserController ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/isUserBlocked")
    public HttpStatus isUserBLocked(@RequestParam String email) {
        try {
            return service.isBlocked(email);
        } catch (Exception e) {
            log.error("Exception in isUserBLocked in UserController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        try {
            return service.findUserById(id);
        } catch (Exception e) {
            log.error("Exception in getUser in UserController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private @NotNull UserRegisterReq getUserRegisterReq(LoginRequest loginRequest) {
        UserRegisterReq userRegisterReq = new UserRegisterReq();
        userRegisterReq.setEmail(loginRequest.getEmail());
        userRegisterReq.setPassword(loginRequest.getPassword());
        return userRegisterReq;
    }
}


