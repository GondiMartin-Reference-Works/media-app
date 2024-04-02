package com.example.mediaApp.auth;

import com.example.mediaApp.model.dto.AppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationResponse {
    String token;
    AppUserDTO user;
}
