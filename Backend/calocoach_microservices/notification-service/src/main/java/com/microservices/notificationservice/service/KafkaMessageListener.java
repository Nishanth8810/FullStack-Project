//package com.microservices.notificationservice.service;
//
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//import com.microservices.notificationservice.dtos.MealPlanDTO;
//import com.microservices.notificationservice.emailService.EmailService;
//
//@Service
//@RequiredArgsConstructor
//public class KafkaMessageListener {
//
//    private final ReportService reportService;
//    private final EmailService emailUtil;
//
//    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);
//
//    @KafkaListener(topics = "notificationTopic", groupId = "jt-group")
//    public void consumeEvent(MealPlanDTO mealPlanDTO) {
//        try {
//            log.info("consumer consume the events of notification topic {} ",
//                    mealPlanDTO.getMeals()
//                    .get(0)
//                            .getMealItems()
//                            .get(0).
//                            getName());
//            String savePath = reportService.getItemReport("pdf", mealPlanDTO);
//            emailUtil.sendEmailWithAttachment(mealPlanDTO.getTrainerEmail(), savePath);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//}
//
