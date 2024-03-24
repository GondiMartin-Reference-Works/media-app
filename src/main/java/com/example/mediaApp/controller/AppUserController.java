package com.example.mediaApp.controller;

import com.example.mediaApp.model.dto.AppUserDTO;
import com.example.mediaApp.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appuser")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppUserController {

    private final AppUserService service;

    @GetMapping()
    public ResponseEntity<List<AppUserDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
}
