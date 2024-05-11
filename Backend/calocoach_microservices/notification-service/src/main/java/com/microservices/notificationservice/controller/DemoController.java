package com.microservices.notificationservice.controller;

import com.microservices.notificationservice.dtos.MealPlanDTO;
import com.microservices.notificationservice.emailService.EmailService;
import com.microservices.notificationservice.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
@Slf4j
public class DemoController {
    @Autowired
    ReportService reportService;
    @Autowired
    EmailService emailService;

    @PostMapping("/getDTO")
    public void getDto(@RequestBody MealPlanDTO mealPlanDTO) {
        try {
            log.info("consumer consume the events of notification topic {} ",
                    mealPlanDTO.getMeals()
                            .get(0)
                            .getMealItems()
                            .get(0).
                            getName());
            String savePath = reportService.getItemReport("pdf", mealPlanDTO);
            emailService.sendEmailWithAttachment(mealPlanDTO.getTrainerEmail(), savePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
        }
    }
}
