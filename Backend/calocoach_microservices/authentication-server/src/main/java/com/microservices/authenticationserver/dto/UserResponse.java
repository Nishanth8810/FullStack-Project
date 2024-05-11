package com.microservices.authenticationserver.dto;

import com.microservices.authenticationserver.modal.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String firstname;
    private String lastname;
    private String email;
    private Set<Role> role;
    private String jwtToken;
    private int active;
    private String profilePic;
    private long userId;


}
