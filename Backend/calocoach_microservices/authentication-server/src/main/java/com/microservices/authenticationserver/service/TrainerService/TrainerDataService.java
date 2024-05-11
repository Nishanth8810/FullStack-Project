package com.microservices.authenticationserver.service.TrainerService;

import com.cloudinary.Cloudinary;
import com.microservices.authenticationserver.dto.PlanDTO.TrainerPlanDTO;
import com.microservices.authenticationserver.dto.TrainerDataDTO.TrainerDataReq;
import com.microservices.authenticationserver.dto.TrainerDataDTO.TrainerResponse;
import com.microservices.authenticationserver.modal.Plans.Plan;
import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.modal.trainerEntity.ProfessionalInformation;
import com.microservices.authenticationserver.modal.trainerEntity.TrainerPayment;
import com.microservices.authenticationserver.repository.PlanRepository;
import com.microservices.authenticationserver.repository.ProfessionalInformationRepo;
import com.microservices.authenticationserver.repository.TrainerPaymentRepo;
import com.microservices.authenticationserver.repository.UserRepository;
import com.microservices.authenticationserver.service.ServiceInterfaces.TrainerDataServiceInterface;
import jakarta.annotation.Resource;
import org.apache.hc.core5.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TrainerDataService implements TrainerDataServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfessionalInformationRepo professionalInformationRepo;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private TrainerPaymentRepo trainerPaymentRepo;
    @Resource
    private Cloudinary cloudinary;


    @Override
    public int saveData(TrainerDataReq req, MultipartFile imageFile) {
        User user = userRepository.findByEmail(req.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String imageUrl = uploadFile(imageFile);
        user.setProfilePicUrl(imageUrl);
        userRepository.save(user);
        return HttpStatus.SC_CREATED;
    }

    @Override
    public List<TrainerResponse> getAllTrainers() {
        List<User> trainers = userRepository.findByRoleRoleName("TRAINER");
        List<TrainerResponse> trainerResponses = new ArrayList<>();
        for (User trainer : trainers) {
            ProfessionalInformation pf = professionalInformationRepo.findByUser(trainer);
            if (pf.getPaymentCompleted() != 0) {
                trainerResponses.add(toDTO(trainer, pf));
            }
        }
        return trainerResponses;
    }
    public List<TrainerResponse> getRandomFiveTrainers() {
        List<User> trainers = userRepository.findByRoleRoleName("TRAINER");

        // Shuffle the list of trainers
        Collections.shuffle(trainers);

        List<TrainerResponse> trainerResponses = new ArrayList<>();
        int count = 0;
        for (User trainer : trainers) {
            ProfessionalInformation pf = professionalInformationRepo.findByUser(trainer);
            if (pf.getPaymentCompleted() != 0) {
                trainerResponses.add(toDTO(trainer, pf));
                count++;
                if (count == 5) {
                    break;  // Stop adding trainers once 5 trainers are added
                }
            }
        }
        return trainerResponses;
    }

    @Override
    public Page<TrainerResponse> findAllTrainers(Pageable pageable) {
        Page<User> trainersPage = userRepository.findByRoleRoleName("TRAINER", pageable);
        List<TrainerResponse> trainerResponses = new ArrayList<>();
        for (User trainer : trainersPage) {
            ProfessionalInformation pf = professionalInformationRepo.findByUser(trainer);
            if (pf.getPaymentCompleted() != 0) {
                trainerResponses.add(toDTO(trainer, pf));
            }
        }
        return new PageImpl<>(trainerResponses, pageable, trainersPage.getTotalElements());
    }

    @Override
    public ResponseEntity<TrainerResponse> getTrainer(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        ProfessionalInformation pf = professionalInformationRepo.findByUser(user);
        return ResponseEntity.ok(toDTO(user, pf));
    }

    @Override
    public ResponseEntity<TrainerResponse> getTrainerDetails(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        ProfessionalInformation pf = professionalInformationRepo.findByUser(user);
        return ResponseEntity.ok(toDTO(user, pf));
    }

    @Override
    public org.springframework.http.HttpStatus editUser(TrainerResponse req, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        ProfessionalInformation pf = professionalInformationRepo.findByUser(user);
        pf.setCertification(req.getCertification());
        pf.setEducation(req.getEducation());
        pf.setSchedule(req.getSchedule());
        pf.setSpecializations(req.getSpecializations());
        pf.setPreferredCommunication(req.getPreferredCommunication());
        pf.setPreviousEmployment(req.getPreviousEmployment());
        pf.setYearsOfExperience(req.getYearsOfExperience());
        pf.setDescription(req.getDescription());
        professionalInformationRepo.save(pf);
        return org.springframework.http.HttpStatus.ACCEPTED;
    }

    private ProfessionalInformation saveProfessionalInformation(TrainerDataReq req, User user) {
        ProfessionalInformation pf = new ProfessionalInformation();
        pf.setUser(user);
        pf.setCertification(req.getCertification());
        pf.setEducation(req.getEducation());
        pf.setSchedule(req.getSchedule());
        pf.setSpecializations(req.getSpecializations());
        pf.setPreferredCommunication(req.getPreferredCommunication());
        pf.setPreviousEmployment(req.getPreviousEmployment());
        pf.setYearsOfExperience(req.getYearsOfExperience());
        pf.setDescription(req.getDescription());
        pf.setPaymentCompleted(0);
        return professionalInformationRepo.save(pf);
    }


    private TrainerResponse toDTO(User user,
                                  ProfessionalInformation professionalInformation) {
        TrainerResponse dto = getTrainerResponse(user);
        getPaymentInformation(user, dto);
        getProfessionalInformation(professionalInformation, dto);
        return dto;
    }

    private void getPaymentInformation(User user, TrainerResponse dto) {
        TrainerPayment payment = user.getTrainer();
        if (payment != null) {
            dto.setAmount(payment.getPaymentAmount());
            dto.setPlanName(payment.getPlan().getPlanName());
            dto.setMemberSince(payment.getPaymentDate().toString());
        }
    }

    private void getProfessionalInformation(ProfessionalInformation professionalInformation, TrainerResponse dto) {
        if (professionalInformation != null) {
            dto.setProfessionalInformationId(professionalInformation.getId());
            dto.setCertification(professionalInformation.getCertification());
            dto.setSpecializations(professionalInformation.getSpecializations());
            dto.setYearsOfExperience(professionalInformation.getYearsOfExperience());
            dto.setPreviousEmployment(professionalInformation.getPreviousEmployment());
            dto.setEducation(professionalInformation.getEducation());
            dto.setSchedule(professionalInformation.getSchedule());
            dto.setPreferredCommunication(professionalInformation.getPreferredCommunication());
            dto.setDescription(professionalInformation.getDescription());
        }
    }

    private @NotNull TrainerResponse getTrainerResponse(User user) {
        TrainerResponse dto = new TrainerResponse();
        dto.setImageUrl(user.getProfilePicUrl());
        dto.setUserId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setActive(user.getActive());
        return dto;
    }

    public int saveTrainerPlan(TrainerPlanDTO trainerPlanDTO) {
        try {
            User user = userRepository.findByEmail(trainerPlanDTO.getId()).orElseThrow(() -> new RuntimeException("User not found"));
            ProfessionalInformation pf = professionalInformationRepo.findByUser(user);
            Plan plan = planRepository.findByPlanName(trainerPlanDTO.getPlanName());
            saveTrainerPlan(trainerPlanDTO, user, plan, pf);
            return HttpStatus.SC_CREATED;
        } catch (Exception e) {
            return HttpStatus.SC_INTERNAL_SERVER_ERROR;
        }
    }

    private void saveTrainerPlan(TrainerPlanDTO trainerPlanDTO, User user, Plan plan, ProfessionalInformation pf) {
        TrainerPayment tp = new TrainerPayment();
        tp.setTrainer(user);
        tp.setPlan(plan);
        tp.setPaymentAmount(trainerPlanDTO.getAmount());
        tp.setPaymentDate(LocalDateTime.now());
        trainerPaymentRepo.save(tp);
        pf.setPaymentCompleted(1);
        professionalInformationRepo.save(pf);
    }

    public String uploadFile(MultipartFile file) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", "profileimage");
            var uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
