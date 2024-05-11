package com.microservices.authenticationserver.service.ServiceInterfaces;

import com.microservices.authenticationserver.dto.PlanDTO.TrainerPlanDTO;
import com.microservices.authenticationserver.dto.TrainerDataDTO.TrainerDataReq;
import com.microservices.authenticationserver.dto.TrainerDataDTO.TrainerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TrainerDataServiceInterface {

    int saveData(TrainerDataReq req, MultipartFile imageFile);

    List<TrainerResponse> getAllTrainers();

    Page<TrainerResponse> findAllTrainers(Pageable pageable);

    int saveTrainerPlan(TrainerPlanDTO trainerPlanDTO);

    ResponseEntity<TrainerResponse> getTrainer(long id);

    ResponseEntity<TrainerResponse> getTrainerDetails(String email);

    org.springframework.http.HttpStatus editUser(TrainerResponse req, String email);


}
