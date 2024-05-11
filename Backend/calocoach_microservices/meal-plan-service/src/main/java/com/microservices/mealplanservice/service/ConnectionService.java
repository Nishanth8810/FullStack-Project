package com.microservices.mealplanservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.microservices.mealplanservice.clientServices.FeignClientService;
import com.microservices.mealplanservice.dto.ConnectionReqDto;
import com.microservices.mealplanservice.dto.TrainersClientDTO;
import com.microservices.mealplanservice.dto.clientResponseDTO.ClientResponse;
import com.microservices.mealplanservice.enums.status;
import com.microservices.mealplanservice.modal.Client;
import com.microservices.mealplanservice.modal.Connection;
import com.microservices.mealplanservice.modal.MealPlan;
import com.microservices.mealplanservice.repository.ClientRepository;
import com.microservices.mealplanservice.repository.ConnectionRepository;
import com.microservices.mealplanservice.repository.MealPlanRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {
    private final ConnectionRepository connectionRepository;
    private final FeignClientService feignClientService;
    private final ClientRepository clientRepository;
    private final MealPlanRepository mealPlanRepository;

    public HttpStatus requestConnection(ConnectionReqDto dto) {
        Connection connection = new Connection();
        connection.setTrainerId(dto.getTrainerId());
        connection.setUserId(dto.getUserId());
        connection.setStatus(status.PENDING.name());
        connectionRepository.save(connection);
        log.info("Connection requested successfully.");
        return HttpStatus.CREATED;
    }

    public ResponseEntity<List<TrainersClientDTO>> getALlClients(Long id) {
        List<Connection> connections = connectionRepository.findByTrainerId(id);
        List<TrainersClientDTO> trainersClientDTO = new ArrayList<>();
        for (Connection connection : connections) {
            if (!Objects.equals(connection.getStatus(), status.ACCEPTED.name())) {
                trainersClientDTO.add(toDto(connection));
            }
        }
        return ResponseEntity.ok(trainersClientDTO);
    }


    private TrainersClientDTO toDto(Connection connection) {
        ResponseEntity<TrainersClientDTO> trainersClientDTO = feignClientService.getUser(connection.getUserId());
        return trainersClientDTO.getBody();
    }


    public HttpStatus acceptRequest(Long trainerId, Long userId) {
        Connection connection = connectionRepository.findByTrainerIdAndUserId(trainerId, userId);
        connection.setStatus(status.ACCEPTED.name());
        connectionRepository.save(connection);
        Client client = new Client();
        client.setTrainerId(trainerId);
        client.setClientSince(LocalDateTime.now());
        client.setUserId(userId);
        clientRepository.save(client);
        log.info("Connection accepted successfully.");
        return HttpStatus.ACCEPTED;
    }

    public HttpStatus rejectRequest(Long trainerId, Long userId) {
        Connection connection = connectionRepository.findByTrainerIdAndUserId(trainerId, userId);
        connectionRepository.deleteById(connection.getId());
        return HttpStatus.OK;
    }

    public HttpStatus getConnection(Long trainerId, Long userId) {
        Connection connection = connectionRepository.findByTrainerIdAndUserId(trainerId, userId);
        if (connection != null) {
            if (Objects.equals(connection.getStatus(), status.PENDING.name())) {
                return HttpStatus.IM_USED;
            }
            if (Objects.equals(connection.getStatus(), status.ACCEPTED.name())) {
                return HttpStatus.ACCEPTED;
            }
            if (Objects.equals(connection.getStatus(), status.IGNORED.name())) {
                return HttpStatus.ALREADY_REPORTED;
            }

        } else {
            return HttpStatus.CONTINUE;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ResponseEntity<List<ClientResponse>> getALlAcceptedClients(long id) {
        List<Client> clients = clientRepository.findByTrainerId(id);
        ArrayList<ClientResponse> clientResponses = new ArrayList<>();
        for (Client client : clients) {
            ClientResponse cr = new ClientResponse();
            getAllClientResponses(client, cr, clientResponses);
        }
        log.info("getALlAcceptedClients successfully.");
        return ResponseEntity.ok(clientResponses);

    }

    private void getAllClientResponses(Client client, ClientResponse cr, ArrayList<ClientResponse> clientResponses) {
        ResponseEntity<TrainersClientDTO> trainersClientDTO = feignClientService.getUser(client.getUserId());
        cr.setClientSince(client.getClientSince());
        cr.setTrainerId(client.getTrainerId());
        cr.setUserId(client.getUserId());
        cr.setClientId(client.getId());
        cr.setUserEmail(Objects.requireNonNull(trainersClientDTO.getBody()).getEmail());
        clientResponses.add(cr);
    }

    public HttpStatus deleteConnection(Long trainerId, Long userId) {
        Connection connection = connectionRepository.findByTrainerIdAndUserId(trainerId, userId);
        if (connection != null) {
            Client client = clientRepository.findByTrainerIdAndUserId(trainerId, userId);
            List<MealPlan> mealPlans = mealPlanRepository.findByClient(client);
            for (MealPlan mealPlan : mealPlans) {
                mealPlanRepository.delete(mealPlan);
            }
            connectionRepository.delete(connection);
            clientRepository.delete(client);
            log.info("deletedConnection successfully.");
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
