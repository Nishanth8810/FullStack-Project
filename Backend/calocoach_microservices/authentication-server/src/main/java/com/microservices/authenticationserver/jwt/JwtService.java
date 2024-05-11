package com.microservices.authenticationserver.jwt;

import com.microservices.authenticationserver.config.CustomUserDetails;
import com.microservices.authenticationserver.dto.UserRegisterReq;
import com.microservices.authenticationserver.dto.UserResponse;
import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.modal.trainerEntity.ProfessionalInformation;
import com.microservices.authenticationserver.repository.ProfessionalInformationRepo;
import com.microservices.authenticationserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ProfessionalInformationRepo professionalInformationRepo;
    private final AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetails customUserDetails;


    public ResponseEntity<UserResponse> createJwtToken(UserRegisterReq req) {
        try {
            com.microservices.authenticationserver.modal.User user = userRepository.findByEmailIgnoreCase(req.getEmail()).orElseThrow();
            if (user.getActive() == 0) {
                return ResponseEntity.status(HttpStatus.SC_FORBIDDEN).build();
            }
            if (user.getRole().stream().anyMatch(role -> role.getRoleName().equals("TRAINER"))) {
                ProfessionalInformation pf = professionalInformationRepo.findByUser(user);
                if (pf.getPaymentCompleted() == 0) {
                    return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).build();
                }
            }
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            } catch (AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).build();
            }
            String newGeneratedToken = jwtToken(req);
            return ResponseEntity.ok(new UserResponse(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole(),
                    newGeneratedToken,
                    user.getActive(),
                    user.getProfilePicUrl(),
                    user.getId()
            ));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
        }
    }

    public String jwtToken(UserRegisterReq req) {
        final UserDetails userDetails = customUserDetails.loadUserByUsername(req.getEmail());
        User user = userRepository.findByEmail(req.getEmail()).orElseThrow();
        return jwtUtil.generateToken(userDetails, user.getId());
    }


}
