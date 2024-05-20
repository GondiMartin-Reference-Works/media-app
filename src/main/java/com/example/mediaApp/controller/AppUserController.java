package com.example.mediaApp.controller;

import com.example.mediaApp.converter.AppUserConverter;
import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "${base.frontend.url}")
public class AppUserController {

    private final AppUserService service;
    private final AppUserConverter appUserConverter;

    @GetMapping()
    public ResponseEntity<List<AppUserDTO>> getAll(){
        return ResponseEntity.ok(service.getAll().stream()
                .map(appUserConverter::convertFromEntityToDTO)
                .toList()
        );
    }

    @PutMapping("/{id}")
    public HttpStatus putUser(@PathVariable Long id, @RequestBody AppUserDTO user){
        Optional<AppUserEntity> appUserEntity = service.updateUser(id, user);
        return appUserEntity.isPresent() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
    }
}
