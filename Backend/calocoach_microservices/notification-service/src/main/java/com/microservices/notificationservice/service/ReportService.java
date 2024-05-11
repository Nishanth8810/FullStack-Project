package com.microservices.notificationservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.microservices.notificationservice.dtos.MealDTO;
import com.microservices.notificationservice.dtos.MealPlanDTO;
import jakarta.annotation.Resource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Resource
    private Cloudinary cloudinary;

    public String getItemReport(String reportFormat, MealPlanDTO mealPlanDTO) throws JRException, FileNotFoundException {
        List<MealPlanDTO> mealPlanDTOS = new ArrayList<>();
        mealPlanDTOS.add(mealPlanDTO);

        byte[] reportBytes = generateReportBytes(mealPlanDTOS, reportFormat);


        return uploadReportToCloudinary(reportBytes, reportFormat, mealPlanDTOS.get(0).getId());
    }

    private byte[] generateReportBytes(List<MealPlanDTO> mealPlanDTOS, String reportFormat) throws JRException, FileNotFoundException {
        File mainReportFile = ResourceUtils.getFile("classpath:mealPlan.jrxml");
        JasperReport mainReport = JasperCompileManager.compileReport(mainReportFile.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(mealPlanDTOS);
        Map<String, Object> parameters = getStringObjectMap(mealPlanDTOS);

        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
//            return JasperExportManager.exportReportToHtmlFile(jasperPrint);
            System.out.println("jb");
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            return JasperExportManager.exportReportToPdf(jasperPrint);
        }

        return null;
    }

    private String uploadReportToCloudinary(byte[] reportBytes, String reportFormat, long reportId) {
        try {
            Map<String, String> options = new HashMap<>();
            options.put("resource_type", "auto");
            options.put("public_id", "mealplan_" + reportId);
            options.put("format", reportFormat);

            // Upload the report to Cloudinary
            var map = cloudinary.uploader().uploadLarge(new ByteArrayInputStream(reportBytes), options);
            String version = String.valueOf(System.currentTimeMillis() / 1000);
            // Generate the Cloudinary URL with version and timestamp
            String cloudinaryUrl = (String) map.get("secure_url");

            return cloudinaryUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }







    private Map<String, Object> getStringObjectMap(List<MealPlanDTO> mealPlanDTOS) {
        Map<String, Object> parameters = new HashMap<>();

        List<MealDTO> meals = mealPlanDTOS.get(0).getMeals();
        for (int i = 0; i < meals.size(); i++) {
            String idParam = "id" + (i + 1);
            String nameParam = "name" + (i + 1);
            parameters.put(idParam, String.valueOf(i + 1));
            parameters.put(nameParam, meals.get(i).getMealItems().get(0).getName());
        }

        parameters.put("title", "Item Report");
        return parameters;
    }

}
