package com.example.mediaApp.controller;

import com.example.mediaApp.model.dto.FriendRequestDTO;
import com.example.mediaApp.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FriendRequestController {
    private final FriendRequestService service;

    @GetMapping()
    public ResponseEntity<List<FriendRequestDTO>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<FriendRequestDTO> create(@RequestBody Emails ids){
        return ResponseEntity.ok(service.create(ids).orElse(new FriendRequestDTO()));
    }


    public record Emails(String senderEmail, String receiverEmail){}
}
