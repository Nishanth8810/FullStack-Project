package com.microservices.communicationservice.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    private String nickName;
    private String fullName;
    private Status status;
}
