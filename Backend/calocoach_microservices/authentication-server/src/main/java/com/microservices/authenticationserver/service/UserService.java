package com.microservices.authenticationserver.service;

import com.microservices.authenticationserver.dto.*;
import com.microservices.authenticationserver.jwt.JwtService;
import com.microservices.authenticationserver.jwt.JwtUtil;
import com.microservices.authenticationserver.modal.OtpEntity;
import com.microservices.authenticationserver.modal.Role;
import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.repository.OtpRepo;
import com.microservices.authenticationserver.repository.RoleRepository;
import com.microservices.authenticationserver.repository.UserRepository;
import com.microservices.authenticationserver.service.ServiceInterfaces.UserServiceInterface;
import com.microservices.authenticationserver.service.emailService.EmailService;
import com.microservices.authenticationserver.service.emailService.OtpService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final OtpService otpService;
    private final OtpRepo otpRepo;
    private final JwtService jwtService;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserResponse createdUser(UserRegisterReq req, String roles) throws Exception {
        User user = new User();
        return getUserResponse(req, roles, user);
    }


    @Override
    public boolean existsByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return false;
        }

        if (user.getOtpEntity() != null) {
            userRepository.delete(user);
        }
        return userRepository.existsByEmail(email);
    }


    @Override
    public void updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setEmail(updatedUser.getEmail());
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<JwtResponse> verify(OtpReq req) {
        try {
            User user = userRepository.findByEmail(req.getEmail())
                    .orElseThrow(() -> new
                            UsernameNotFoundException("User not found"));

            OtpEntity otpEntity = otpRepo.findByUser(user);
            ResponseEntity<JwtResponse> REQUEST_TIMEOUT = getOTPStatus(otpEntity);
            if (REQUEST_TIMEOUT != null) return REQUEST_TIMEOUT;

            if (Objects.equals(otpEntity.getOtp(), req.getOtpNumber())) {
                JwtResponse jwtResponse = getJwtResponse(user, otpEntity);

                return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public HttpStatus verifyEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return (HttpStatus.NOT_FOUND);
        }
        sendOTP(user, Optional.empty());
        return HttpStatus.OK;

    }

    @Override
    public HttpStatus changePass(ChangePassReq changePassReq) {
        if (jwtUtil.getUserNameFromToken(changePassReq.getEmailToken()).equals(changePassReq.getEmail())) {
            User user = userRepository.findByEmail(changePassReq.getEmail()).get();
            user.setPassword(bCryptPasswordEncoder.encode(changePassReq.getNewPassword()));
            userRepository.save(user);
            return HttpStatus.OK;
        }
        return HttpStatus.BAD_REQUEST;

    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().stream().anyMatch(role -> role.getRoleName().equals("USER"))) {
                userResponseList.add(toUserResponseDto(user));
            }
        }
        return userResponseList;
    }

    @Override
    public HttpStatus unblock(String userId) {
        User user = userRepository.findByEmail(userId).orElseThrow();
        user.setActive(1);
        userRepository.save(user);
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus block(String userId) {
        User user = userRepository.findByEmail(userId).orElseThrow();
        user.setActive(0);
        userRepository.save(user);
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus resendOTP(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        OtpEntity otpEntity = otpRepo.findByUser(user);
        sendOTP(user, Optional.ofNullable(otpEntity));
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus isBlocked(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return HttpStatus.NOT_FOUND;
        }
        int isActive = user.getActive();
        if (isActive == 0) {
            return HttpStatus.FORBIDDEN;
        }
        return HttpStatus.OK;
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable) {

        Page<User> usersPage = userRepository.findByRoleRoleName("USER", pageable);

        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : usersPage.getContent()) {
            userResponseList.add(toUserResponseDto(user));
        }
        return new PageImpl<>(userResponseList, pageable, usersPage.getTotalElements());
    }

    @Override
    public ResponseEntity<UserResponse> findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new ResponseEntity<>(toUserResponseDto(user), HttpStatus.OK);
    }


    private void sendOTP(User user, Optional<OtpEntity> otpEntityOptional) {
        String otp = otpService.generateOtp();
        emailService.sendOtpEmail(user.getEmail(), otp);

        OtpEntity otpEntity;
        otpEntity = otpEntityOptional.orElseGet(OtpEntity::new);

        otpEntity.setOtpActive(false);
        otpEntity.setOtpGeneratedTime(LocalDateTime.now());
        otpEntity.setUser(user);
        otpEntity.setOtp(otp);
        otpRepo.save(otpEntity);
    }

    private @Nonnull JwtResponse getJwtResponse(User user, OtpEntity otpEntity) {
        JwtResponse jwtResponse = new JwtResponse();
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstname(user.getFirstName());
        userResponse.setRole(user.getRole());
        userResponse.setLastname(user.getLastName());
        UserRegisterReq userRegisterReq = new UserRegisterReq();
        userRegisterReq.setEmail(user.getEmail());
        jwtResponse.setJwtToken(jwtService.jwtToken(userRegisterReq));
        jwtResponse.setUserResponse(userResponse);
        user.setOtpEntity(null);
        otpRepo.delete(otpEntity);

        user.setActive(1);
        userRepository.save(user);
        return jwtResponse;
    }

    private @Nullable ResponseEntity<JwtResponse> getOTPStatus(OtpEntity otpEntity) {
        if (otpEntity == null || Duration.between(otpEntity.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() > 60) {
            if (otpEntity != null) {
                otpRepo.delete(otpEntity);
            }
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
        return null;
    }


    private UserResponse toUserResponseDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setActive(user.getActive());
        userResponse.setFirstname(user.getFirstName());
        userResponse.setLastname(user.getLastName());
        userResponse.setProfilePic(user.getProfilePicUrl());
        userResponse.setUserId(user.getId());

        return userResponse;
    }

    private @Nullable UserResponse getUserResponse(UserRegisterReq req,
                                                   String roles, User user) {
        user.setEmail(req.getEmail().trim());
        user.setFirstName(req.getFirstname().trim());
        user.setLastName(req.getLastname().trim());
        user.setPassword(bCryptPasswordEncoder.encode(req.getPassword().trim()));
        user.setCreatedAt(LocalDateTime.now());
        Role role = roleRepository.findById(roles).orElseThrow();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setActive(0);
        userRepository.save(user);
        sendOTP(user, Optional.empty());
        return jwtService.createJwtToken(req).getBody();
    }

}
