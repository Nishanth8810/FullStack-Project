package com.microservices.mealplanservice.service;

import com.microservices.mealplanservice.emailService.EmailService;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import com.microservices.mealplanservice.clientServices.FeignClientService;
import com.microservices.mealplanservice.dto.TrainersClientDTO;
import com.microservices.mealplanservice.dto.mealDto.*;
import com.microservices.mealplanservice.modal.Client;
import com.microservices.mealplanservice.modal.Meal;
import com.microservices.mealplanservice.modal.MealItem;
import com.microservices.mealplanservice.modal.MealPlan;
import com.microservices.mealplanservice.repository.ClientRepository;
import com.microservices.mealplanservice.repository.MealItemRepository;
import com.microservices.mealplanservice.repository.MealPlanRepository;
import com.microservices.mealplanservice.repository.MealRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class MealPlanService {

    private final ClientRepository clientRepository;
    private final MealPlanRepository mealPlanRepository;
    private final MealItemRepository mealItemRepository;
    private final MealRepository mealRepository;
    private final FeignClientService feignClientService;
    private final ReportService reportService;
    private final EmailService emailService;
//    private final KafkaTemplate<String, Object> template;

    @Transactional
    public MealPlanDTO createMealPlan(@Nonnull MealPlanRequestDTO mealPlanRequestDTO) {
        log.info("Creating meal plan...");
        Client client = clientRepository.findByTrainerIdAndUserId(
                mealPlanRequestDTO.getTrainerId(),
                mealPlanRequestDTO.getClientId()
        );
        MealPlan mealPlan = createMealPlan(mealPlanRequestDTO, client);
        createMealsAndMealItems(mealPlanRequestDTO, mealPlan);
        mealPlanRepository.save(mealPlan);
        MealPlanDTO mealPlanDTO = convertToMealPlanDTO(mealPlan);
//        sendToNotificationService(mealPlanDTO);
//        feignClientService.postMealDTO(mealPlanDTO);
            sendMessage(mealPlanDTO);

        log.info("Meal plan created: {}", mealPlanDTO);
        return mealPlanDTO;
    }

    public void sendMessage(MealPlanDTO mealPlanDTO) {
        try {
            String savePath = reportService.getItemReport("pdf", mealPlanDTO);
            emailService.sendEmailWithAttachment(mealPlanDTO.getTrainerEmail(), savePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
        }
    }

    @Nonnull
    private MealPlanDTO convertToMealPlanDTO(MealPlan mealPlan) {
        MealPlanDTO mealPlanDTO = new MealPlanDTO();
        getMealPlanDTO(mealPlan, mealPlanDTO);
        List<MealDTO> mealDTOs = getMealDTOS(mealPlan);
        mealPlanDTO.setTrainerEmail(mealPlan.getUserEmail());
        mealPlanDTO.setMeals(mealDTOs);
        return mealPlanDTO;
    }

    public List<MealPlanDTO> getMealPlan(long id) {
        Client client = clientRepository.findByUserId(id);
        List<MealPlan> mealPlanList = mealPlanRepository.findByClient(client);
        List<MealPlanDTO> mealPlanDTOS = new ArrayList<>();
        assert mealPlanList != null;
        for (MealPlan mealPlan : mealPlanList) {
            mealPlanDTOS.add(convertToMealPlanDTO(mealPlan));
        }
        return mealPlanDTOS;
    }

    public ResponseEntity<List<MealPlanResponse>> getAllPlans(long id) {
        List<Client> clients = clientRepository.findByTrainerId(id);
        List<MealPlan> mealPlans = mealPlanRepository.findAllByClientIn(clients);
        List<MealPlanResponse> mealPlanResponses = new ArrayList<>();
        for (MealPlan mealPlan : mealPlans) {
            MealPlanResponse mp = new MealPlanResponse();
            mp.setPlanName(mealPlan.getPlanName());
            ResponseEntity<TrainersClientDTO> trainersClientDTO = feignClientService
                    .getUser(mealPlan.getClient().getUserId());
            mp.setCreated(mealPlan.getStartDate());
            mp.setPlanId(mealPlan.getId());
            mp.setForDate(mealPlan.getForDate());
            mp.setTotalCalories(mealPlan.getTotalCalories());
            mp.setPlanId(mealPlan.getId());
            mp.setUserEmail(Objects.requireNonNull(trainersClientDTO.getBody()).getEmail());
            mealPlanResponses.add(mp);
        }
        return ResponseEntity.ok(mealPlanResponses);


    }

    public MealPlanDTO getMealPlanByPlanId(long id) {
        MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow();
        return convertToMealPlanDTO(mealPlan);

    }

    public HttpStatus deleteMealPlanByPlanId(long id) {
        MealPlan mealPlan = mealPlanRepository.findById(id).orElseThrow();
        mealPlanRepository.delete(mealPlan);
        return HttpStatus.ACCEPTED;
    }

    private void createMealsAndMealItems(@Nonnull MealPlanRequestDTO mealPlanRequestDTO, MealPlan mealPlan) {
        List<MealDTO> mealDTOs = mealPlanRequestDTO.getMeals();
        for (MealDTO mealDTO : mealDTOs) {
            Meal meal = new Meal();
            meal.setMealType(mealDTO.getMealType());
            meal.setMealPlan(mealPlan);
            List<MealItemDTO> mealItemDTOs = mealDTO.getMealItems();
            List<MealItem> mealItems = new ArrayList<>();
            for (MealItemDTO mealItemDTO : mealItemDTOs) {
                MealItem mealItem = new MealItem();
                mealItem.setRecipe(mealItemDTO.getRecipeId());
                mealItem.setMeal(meal);
                mealItems.add(mealItem);
                mealItemRepository.save(mealItem);
            }
            meal.setMealItems(mealItems);
            meal.setMealPlan(mealPlan);
            mealRepository.save(meal);
        }
    }

    private MealPlan createMealPlan(MealPlanRequestDTO mealPlanRequestDTO, Client client) {
        MealPlan mealPlan = new MealPlan();
        ResponseEntity<TrainersClientDTO> user =
                feignClientService.getUser(mealPlanRequestDTO.getClientId());
        mealPlan.setUserEmail(Objects.requireNonNull(user.getBody()).getEmail());
        mealPlan.setPlanName(mealPlanRequestDTO.getPlanName());
        mealPlan.setClient(client);
        mealPlan.setNote(mealPlanRequestDTO.getNote());
        mealPlan.setForDate(mealPlanRequestDTO.getForDate());
        mealPlan.setTotalCalories(mealPlanRequestDTO.getTotalCalories());
        mealPlan.setStartDate(LocalDate.now());
        return mealPlan;
    }

    @Nonnull
    private List<MealDTO> getMealDTOS(MealPlan mealPlan) {
        List<Meal> meals = mealRepository.findByMealPlan(mealPlan);
        List<MealDTO> mealDTOs = new ArrayList<>();
        for (Meal meal : meals) {
            MealDTO mealDTO = new MealDTO();
            mealDTO.setMealType(meal.getMealType());

            List<MealItem> mealItems = mealItemRepository.findByMeal(meal);
            List<MealItemDTO> mealItemDTOs = new ArrayList<>();
            for (MealItem mealItem : mealItems) {
                ResponseEntity<MealItemDTO> recipe = feignClientService.getRecipe(mealItem.getRecipe());
                MealItemDTO body = recipe.getBody();

                MealItemDTO mealItemDTO = new MealItemDTO();
                assert body != null;
                mealItemDTO.setName(body.getName());
                mealItemDTO.setImageUrl(body.getImageUrl());
                mealItemDTO.setNutrients(body.getNutrients());
                mealItemDTO.setRecipeId(mealItem.getRecipe());
                mealItemDTOs.add(mealItemDTO);
            }

            mealDTO.setMealItems(mealItemDTOs);
            mealDTOs.add(mealDTO);
        }
        return mealDTOs;
    }

    private void getMealPlanDTO(MealPlan mealPlan, MealPlanDTO mealPlanDTO) {
        mealPlanDTO.setId(mealPlan.getId());
        mealPlanDTO.setNote(mealPlan.getNote());
        mealPlanDTO.setTrainerId(mealPlan.getClient().getTrainerId());
        mealPlanDTO.setClientId(mealPlan.getClient().getUserId());
        mealPlanDTO.setForDate(mealPlan.getForDate());
        mealPlanDTO.setNote(mealPlan.getNote());
        mealPlanDTO.setTotalCalories(mealPlan.getTotalCalories());
        mealPlanDTO.setPlanId(mealPlan.getId());
        mealPlanDTO.setPlanName(mealPlan.getPlanName());
    }

//    private void sendToNotificationService(MealPlanDTO mealPlanDTO) {
//        CompletableFuture<SendResult<String, Object>> future = template.send("notificationTopic", mealPlanDTO);
//        future.whenComplete((result, ex) -> {
//            if (ex == null) {
//                log.info("Sent message from notification: [{}] with offset: [{}]", mealPlanDTO.getPlanId(),
//                        result.getRecordMetadata().offset());
//            } else {
//                log.error("Unable to send message: [{}] due to: {}", mealPlanDTO.getPlanId(), ex.getMessage());
//            }
//        });
//    }
}


