package com.microservices.authenticationserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private LocalDate timeStamp; // The date when the error occurred
    private int status;          // The HTTP status code of the error
    private String error;        // A brief description of the error status
    private String message;      // A detailed error message
    private String path;         // The API path where the error occurred

}