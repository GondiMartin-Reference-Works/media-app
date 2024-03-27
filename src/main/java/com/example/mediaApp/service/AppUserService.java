package com.example.mediaApp.service;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;

    public List<AppUserDTO> getAll(){
        return repository.findAll()
                .stream().map(user -> new AppUserDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                ))
                .toList();
    }

    public Optional<AppUserDTO> getByEmail(String email){
        return repository.findByEmail(email).map(user -> new AppUserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        ));
    }
}
