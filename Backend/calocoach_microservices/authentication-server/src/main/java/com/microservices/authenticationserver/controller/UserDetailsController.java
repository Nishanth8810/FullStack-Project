package com.microservices.authenticationserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.authenticationserver.clientServices.DTOs.RecipeResponse;
import com.microservices.authenticationserver.clientServices.clientDtos.FeignClientService;
import com.microservices.authenticationserver.dto.ChangePassReq;
import com.microservices.authenticationserver.dto.UserDataDTO.RecipeSaveDTO;
import com.microservices.authenticationserver.dto.UserDataDTO.UserDataReq;
import com.microservices.authenticationserver.dto.UserDataDTO.UserDataResponse;
import com.microservices.authenticationserver.dto.UserResponse;
import com.microservices.authenticationserver.service.UserDataService;
import com.microservices.authenticationserver.service.UserService;
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

import java.util.List;

@RestController
@RequestMapping("/userDetail")
@RequiredArgsConstructor
@Slf4j
public class UserDetailsController {

    private final UserDataService service;
    private final UserService userService;
    private final FeignClientService feignClientService;

    @PostMapping("/saveData")
    public HttpStatus saveData(@RequestParam("userDataReq") String reqJSon,
                               @RequestParam("imageFile") MultipartFile imageFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDataReq req;
        try {
            req = objectMapper.readValue(reqJSon, UserDataReq.class);
            return service.saveData(req, imageFile);
        } catch (JsonProcessingException e) {
            log.error("Json Parsing Exception in saveData method in userDetails controller", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }


    @PostMapping("/saveRecipe")
    public HttpStatus saveRecipe(@RequestBody RecipeSaveDTO recipeSaveDTO) {
        try {
            return service.saveRecipe(recipeSaveDTO);
        } catch (Exception e) {
            log.error("Exception in saveRecipe method in userDetails controller", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    @GetMapping("/getSavedRecipes/{email}")
    public ResponseEntity<List<RecipeResponse>> getAllUserRecipe(@PathVariable String email) {
        try {
            return feignClientService.getUserSavedRecipes(email);
        } catch (Exception e) {
            log.error("Exception in getAllUserRecipe method in userDetails controller", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserResponse>> getPaginatedCards
            (@RequestParam int page,
             @RequestParam int size,
             @RequestParam String sortOrder) {
        try {
            String sortBy = "email";
            Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);
            Page<UserResponse> cards = userService.findAll(pageable);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            log.error("Exception in getPaginatedCards method in userDetails controller", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{email}")
    public HttpStatus editProfile(@PathVariable String email, @RequestBody UserDataResponse userDataResponse) {
        try {
            return service.editUser(userDataResponse, email);
        } catch (Exception e) {
            log.error("Exception in editProfile method in userDetails controller", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PutMapping("/editPicture/{email}")
    public HttpStatus editPicture(@RequestParam("imageFile") MultipartFile imageFile, @PathVariable String email) {
        try {
            return service.editImage(imageFile, email);
        } catch (Exception e) {
            log.error("Exception in editPicture method in userDetails controller", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @PostMapping("/changePass")
    public HttpStatus changePassword(@RequestBody ChangePassReq changePassReq) {
        try {
            return userService.changePass(changePassReq);
        } catch (Exception e) {
            log.error("Exception in changePassword method in userDetails controller", e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/userSetting/{email}")
    public ResponseEntity<UserDataResponse> getUserData(@PathVariable String email) {
        try {
            return service.getUserData(email);
        } catch (Exception e) {
            log.error("Exception in getUserData method in userDetails controller", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
