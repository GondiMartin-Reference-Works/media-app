package com.example.mediaApp.controller;

import com.example.mediaApp.model.dto.FriendListElementDTO;
import com.example.mediaApp.model.entity.FriendConnectionEntity;
import com.example.mediaApp.service.FriendConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class FriendConnectionController {
    private final FriendConnectionService service;

    @GetMapping
    public ResponseEntity<List<FriendListElementDTO>> getAllForEmail(@RequestParam String email){
        return ResponseEntity.ok(service.getAllForEmail(email).stream()
                .map(connection -> new FriendListElementDTO(
                        connection.getFriend().getEmail(),
                        connection.getFriend().getFirstName(),
                        connection.getFriend().getLastName()
                ))
                .toList());
    }

    @DeleteMapping
    public ResponseEntity<FriendListElementDTO> delete(@RequestParam String loggedInUserEmail, @RequestParam String toBeDeletedUserEmail){
        return ResponseEntity.ok(service.delete(loggedInUserEmail, toBeDeletedUserEmail)
                .map(FriendConnectionEntity::getFriend)
                .map(user -> new FriendListElementDTO(
                        user.getEmail(),
                        user.getFirstName(),
                        user.getLastName()
                )).orElse(new FriendListElementDTO()));
    }
}
