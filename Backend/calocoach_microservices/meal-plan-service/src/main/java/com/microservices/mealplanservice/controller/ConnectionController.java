package com.microservices.mealplanservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microservices.mealplanservice.dto.ConnectionReqDto;
import com.microservices.mealplanservice.dto.TrainersClientDTO;
import com.microservices.mealplanservice.dto.clientResponseDTO.ClientResponse;
import com.microservices.mealplanservice.service.ConnectionService;

import java.util.List;

@RestController
@RequestMapping("/connection")
@RequiredArgsConstructor
@Slf4j
public class ConnectionController {

    private final ConnectionService connectionService;

    @PostMapping("/requestConnection")
    public HttpStatus requestConnection(@RequestBody ConnectionReqDto dto) {
        try {
            return connectionService.requestConnection(dto);
        } catch (Exception e) {
            log.error("Exception in requestConnection in ConnectionController: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/getStatusOfConnection")
    public HttpStatus getStatusConnection(@RequestParam("trainerId") Long trainerId,
                                          @RequestParam("userId") Long userId) {
        try {
            return connectionService.getConnection(trainerId, userId);
        } catch (Exception e) {
            log.error("Exception in getStatusConnection in ConnectionController: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/getAllPendingClients/{id}")
    public ResponseEntity<List<TrainersClientDTO>> getAllPendingClients(@PathVariable long id) {
        try {
            return connectionService.getALlClients(id);
        } catch (Exception e) {
            log.error("Exception in getAllPendingClients in ConnectionController: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllClients/{id}")
    public ResponseEntity<List<ClientResponse>> getAllClients(@PathVariable long id) {
        try {
            return connectionService.getALlAcceptedClients(id);
        } catch (Exception e) {
            log.error("Exception in getAllClients in ConnectionController: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/acceptRequest")
    public HttpStatus acceptRequest(@RequestParam("trainerId") Long trainerId,
                                    @RequestParam("userId") Long userId) {
        try {
            return connectionService.acceptRequest(trainerId, userId);
        } catch (Exception e) {
            log.error("Exception in acceptRequest in ConnectionController: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/rejectRequest")
    public HttpStatus rejectRequest(@RequestParam("trainerId") Long trainerId,
                                    @RequestParam("userId") Long userId) {
        try {
            return connectionService.rejectRequest(trainerId, userId);
        } catch (Exception e) {
            log.error("Exception in rejectRequest in ConnectionController: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @DeleteMapping("/stopManaging")
    public HttpStatus deleteConnection(@RequestParam("trainerId") Long trainerId,
                                       @RequestParam("userId") Long userId) {
        try {
            return connectionService.deleteConnection(trainerId, userId);
        } catch (Exception e) {
            log.error("Exception in deleteConnection in ConnectionController: {}", e.getMessage());
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
