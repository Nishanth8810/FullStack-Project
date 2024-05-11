package com.microservices.authenticationserver.service.ServiceInterfaces;

import com.microservices.authenticationserver.dto.UserDataDTO.RecipeSaveDTO;
import com.microservices.authenticationserver.dto.UserDataDTO.UserDataReq;
import com.microservices.authenticationserver.dto.UserDataDTO.UserDataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserDataServiceInterface {
    HttpStatus saveData(UserDataReq req, MultipartFile imageFile);

    HttpStatus saveRecipe(RecipeSaveDTO recipeSaveDTO);

    HttpStatus editUser(UserDataResponse request, String email);

    HttpStatus editImage(MultipartFile imageFile, String email) throws IOException;

    ResponseEntity<UserDataResponse> getUserData(String email);
}
