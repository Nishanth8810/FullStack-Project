package com.microservices.authenticationserver.service;

import com.cloudinary.Cloudinary;
import com.microservices.authenticationserver.dto.UserDataDTO.RecipeSaveDTO;
import com.microservices.authenticationserver.dto.UserDataDTO.UserDataReq;
import com.microservices.authenticationserver.dto.UserDataDTO.UserDataResponse;
import com.microservices.authenticationserver.modal.User;
import com.microservices.authenticationserver.modal.userEntities.Exclusions;
import com.microservices.authenticationserver.modal.userEntities.SavedRecipe;
import com.microservices.authenticationserver.modal.userEntities.UserData;
import com.microservices.authenticationserver.repository.SavedRecipeRepo;
import com.microservices.authenticationserver.repository.UserDataRepository;
import com.microservices.authenticationserver.repository.UserRepository;
import com.microservices.authenticationserver.service.ServiceInterfaces.UserDataServiceInterface;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDataService implements UserDataServiceInterface {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserDataRepository userDataRepository;
    @Autowired
    private SavedRecipeRepo savedRecipeRepo;
    @Resource
    private Cloudinary cloudinary;

    @Override
    public HttpStatus saveData(UserDataReq req, MultipartFile imageFile) {
        try {
            String imageUrl = uploadFile(imageFile);
            User user = userRepository.findByEmail(req.getUserEmail()).orElseThrow(() -> new RuntimeException("User not found"));
            user.setProfilePicUrl(imageUrl);
            return saveUserData(req, user);
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    @Override
    public HttpStatus saveRecipe(RecipeSaveDTO recipeSaveDTO) {
        try {
            User user = userRepository.findByEmail(recipeSaveDTO.getEmail()).orElseThrow();
            SavedRecipe savedRecipe = new SavedRecipe();
            savedRecipe.setRecipeId(recipeSaveDTO.getRecipeId());
            savedRecipe.setUser(user);
            savedRecipeRepo.save(savedRecipe);
            return HttpStatus.CREATED;
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public HttpStatus editUser(UserDataResponse request, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        UserData userData = userDataRepository.findByUser(user);
        return editUser(request, user, userData);

    }

    @Override
    public HttpStatus editImage(MultipartFile imageFile, String email) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        String imageUrl = uploadFile(imageFile);
        user.setProfilePicUrl(imageUrl);
        userRepository.save(user);
        return HttpStatus.ACCEPTED;
    }

    @Override
    public ResponseEntity<UserDataResponse> getUserData(String email) {
        UserDataResponse userDataResponse = new UserDataResponse();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return getUserDataResponse(user, userDataResponse);
    }

    private @Nonnull HttpStatus saveUserData(UserDataReq req, User user) {
        userRepository.save(user);
        UserData userData = new UserData();
        userData.setUser(user);
        saveData(req, userData);

        List<Exclusions> exclusions = new ArrayList<>();

        for (String exclusionName : req.getExclusions()) {
            Exclusions exclusion = new Exclusions();
            exclusion.setExclusionName(exclusionName);
            exclusions.add(exclusion);
        }
        userData.setExclusions(exclusions);
        userDataRepository.save(userData);
        return HttpStatus.ACCEPTED;
    }

    private void saveData(UserDataReq req, UserData userData) {
        userData.setActivityLevel(req.getActivityLevel());
        userData.setAge(req.getAge());
        userData.setHeight(req.getHeight());
        userData.setGender(req.getGender());
        userData.setBodyFat(req.getBodyFat());
        userData.setDietType(req.getPreDiet());
        userData.setWeight(req.getWeight());
    }


    private @Nonnull ResponseEntity<UserDataResponse>
    getUserDataResponse(User user, UserDataResponse userDataResponse) {
        UserData userData = userDataRepository.findByUser(user);
        userDataResponse.setFirstname(user.getFirstName());
        userDataResponse.setLastname(user.getLastName());
        userDataResponse.setProfilePic(user.getProfilePicUrl());
        userDataResponse.setActivityLevel(userData.getActivityLevel());
        userDataResponse.setAge(userData.getAge());
        userDataResponse.setHeight(userData.getHeight());
        userDataResponse.setGender(userData.getGender());
        userDataResponse.setBodyFat(userData.getBodyFat());
        userDataResponse.setPreDiet(userData.getDietType());
        userDataResponse.setWeight(userData.getWeight());
        userDataResponse.setUserEmail(user.getEmail());
        return ResponseEntity.ok(userDataResponse);
    }

    private @Nonnull HttpStatus editUser(UserDataResponse request,
                                         User user,
                                         UserData userData) {
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        userData.setActivityLevel(request.getActivityLevel());
        userData.setAge(request.getAge());
        userData.setHeight(request.getHeight());
        userData.setGender(request.getGender());
        userData.setBodyFat(request.getBodyFat());
        userData.setDietType(request.getPreDiet());
        userData.setWeight(request.getWeight());
        userDataRepository.save(userData);
        return HttpStatus.ACCEPTED;
    }

    public String uploadFile(MultipartFile file) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", "profileimage");
            Map uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

