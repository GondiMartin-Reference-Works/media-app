package com.example.mediaApp.auth;

import com.example.mediaApp.config.JwtService;
import com.example.mediaApp.converter.AppUserConverter;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.Role;
import com.example.mediaApp.repository.AppUserRepository;
import com.example.mediaApp.service.FriendConnectionService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AppUserConverter appUserConverter;

    private static final Logger LOGGER = LogManager.getLogger(FriendConnectionService.class);


    public AuthenticationResponse register(RegisterRequest request) {
        AppUserEntity user = new AppUserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.USER);
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, appUserConverter.convertFromEntityToDTO(user), request.getEmail());
    }

    public AuthenticationResponse authenticate(AuthencticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        AppUserEntity user = (AppUserEntity) authentication.getPrincipal();

        String jwtToken = jwtService.generateToken(user);
        LOGGER.info(STR."Current user's (\{user.getFirstName()} \{user.getLastName()}) token: \{jwtToken}");
        return new AuthenticationResponse(jwtToken, appUserConverter.convertFromEntityToDTO(user), request.getEmail());
    }

}
