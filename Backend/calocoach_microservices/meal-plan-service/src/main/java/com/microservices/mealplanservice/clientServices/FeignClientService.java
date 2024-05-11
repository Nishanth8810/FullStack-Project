package com.microservices.mealplanservice.clientServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.microservices.mealplanservice.dto.TrainersClientDTO;
import com.microservices.mealplanservice.dto.mealDto.MealItemDTO;

@Component
public class FeignClientService {
    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    private HttpHeaders httpHeaders;
    @Value("${app.base-url}")
    String baseUrl;


    public FeignClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<TrainersClientDTO> getUser(long id) {

        String url = baseUrl+"/user/getUser/" + id;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }

    public ResponseEntity<MealItemDTO> getRecipe(long id) {

        String url = baseUrl+"/recipes/food/" + id;
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }

//    public void postMealDTO(MealPlanDTO mealPlanDTO) {
//
//        String url = "http://15.207.14.222:8085/notification/getDTO";
//        HttpEntity<MealPlanDTO> requestEntity = new HttpEntity<>(mealPlanDTO, httpHeaders);
//
//        ResponseEntity<Void> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.POST,
//                requestEntity,
//                Void.class
//        );
//
//    }



}
