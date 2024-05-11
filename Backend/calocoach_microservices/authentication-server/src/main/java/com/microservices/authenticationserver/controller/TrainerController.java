package com.microservices.authenticationserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.authenticationserver.dto.LoginRequest;
import com.microservices.authenticationserver.dto.PlanDTO.PlanResponse;
import com.microservices.authenticationserver.dto.PlanDTO.TrainerPlanDTO;
import com.microservices.authenticationserver.dto.TrainerDataDTO.TrainerDataReq;
import com.microservices.authenticationserver.dto.TrainerDataDTO.TrainerResponse;
import com.microservices.authenticationserver.dto.UserRegisterReq;
import com.microservices.authenticationserver.dto.UserResponse;
import com.microservices.authenticationserver.jwt.JwtService;
import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.service.TrainerService.TrainerDataService;
import com.microservices.authenticationserver.service.UserService;
import com.microservices.authenticationserver.service.planService.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainer")
@RequiredArgsConstructor
@Slf4j
public class TrainerController {

    private final UserService service;
    private final JwtService jwtService;
    private final TrainerDataService trainerDataService;
    private final PlanService planService;

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponse>> findAll() {
        try {
            List<UserResponse> users = service.findAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Exception in findAll method in TrainerController ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserRegisterReq userRegisterReq = new UserRegisterReq();
            userRegisterReq.setEmail(loginRequest.getEmail());
            userRegisterReq.setPassword(loginRequest.getPassword());
            return jwtService.createJwtToken(userRegisterReq);
        } catch (Exception e) {
            log.error("Exception in login in TrainerController ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/saveTrainerData")
    public ResponseEntity<Integer> saveTrainer(@RequestParam("TrainerDataReq") String reqJSon,
                                               @RequestParam("imageFile") MultipartFile imageFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        TrainerDataReq req;
        try {
            req = objectMapper.readValue(reqJSon, TrainerDataReq.class);
            int result = trainerDataService.saveData(req, imageFile);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            log.error("Exception in saveTrainer in TrainerController ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(2);
        } catch (Exception e) {
            log.error("Exception in saveTrainer in TrainerController ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable Long userId,
                                                 @RequestBody User updatedUser) {
        try {
            service.updateUser(userId, updatedUser);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in updateUser  in TrainerController ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/getAllTrainer")
    public List<TrainerResponse> getAllTrainer() {
        try {
            return trainerDataService.getAllTrainers();
        } catch (Exception e) {
            log.error("Exception in updateUser  in TrainerController ", e);
            return new ArrayList<>();
        }
    }
    @GetMapping("/getAllTrainerFive")
    public List<TrainerResponse> getAllTrainerRandom() {
        try {
            return trainerDataService.getRandomFiveTrainers();
        } catch (Exception e) {
            log.error("Exception in updateUser  in TrainerController ", e);
            return new ArrayList<>();
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<TrainerResponse>> getPaginatedCards
            (@RequestParam int page,
             @RequestParam int size,
             @RequestParam String sortOrder) {
        try {
            String sortBy = "email";
            Sort sort = sortOrder
                    .equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<TrainerResponse> cards = trainerDataService.findAllTrainers(pageable);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            log.error("Exception in getPaginatedCards  in TrainerController ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/saveTrainerPlan")
    public int saveTrainerPlan(@RequestBody TrainerPlanDTO trainerPlanDTO) {
        try {
            return trainerDataService.saveTrainerPlan(trainerPlanDTO);
        } catch (Exception e) {
            log.error("Exception in saveTrainer in TrainerController ", e);
            return 2;
        }
    }

    @GetMapping("/getAllPlan")
    public List<PlanResponse> getAllPlan() {
        try {
            return planService.getAllPlanForTrainer();
        } catch (Exception e) {
            log.error("Exception in getAllPlan in TrainerController ", e);
            return new ArrayList<>();
        }
    }


    @GetMapping("/gettrainer/{id}")
    public ResponseEntity<TrainerResponse> getTrainer(@PathVariable long id) {
        try {
            return trainerDataService.getTrainer(id);
        } catch (Exception e) {
            log.error("Exception in getTrainer  in TrainerController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/trainerSetting/{email}")
    public ResponseEntity<TrainerResponse> getTrainerData(@PathVariable String email) {
        try {
            return trainerDataService.getTrainerDetails(email);
        } catch (Exception e) {
            log.error("Exception in getTrainerData in TrainerController ", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{email}")
    public HttpStatus editProfile(@PathVariable String email,
                                  @RequestBody TrainerResponse trainerResponse) {
        try {
            return trainerDataService.editUser(trainerResponse, email);
        } catch (Exception e) {
            log.error("Exception in editProfile in TrainerController ", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}
