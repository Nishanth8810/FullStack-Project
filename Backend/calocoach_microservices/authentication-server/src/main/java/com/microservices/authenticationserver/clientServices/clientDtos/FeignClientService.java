package com.microservices.authenticationserver.clientServices.clientDtos;

import com.microservices.authenticationserver.clientServices.DTOs.RecipeResponse;
import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FeignClientService {
    private final RestTemplate restTemplate;
    @Value("${app.base-url}")
    private String baseUrl;
    @Autowired
    private UserRepository userRepository;

    public FeignClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

    }


    public ResponseEntity<List<RecipeResponse>> getUserSavedRecipes(String email) {
        User userId = userRepository.findByEmail(email).orElseThrow();
        String url = baseUrl + "/recipes/getAllRecipeOfUser?userId=" + userId.getId();
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
    }


}
