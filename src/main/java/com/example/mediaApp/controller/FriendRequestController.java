package com.example.mediaApp.controller;

import com.example.mediaApp.converter.AppFriendUserConverter;
import com.example.mediaApp.converter.FriendConnectionConverter;
import com.example.mediaApp.converter.FriendRequestConverter;
import com.example.mediaApp.model.dto.AppFriendUserDTO;
import com.example.mediaApp.model.dto.FriendConnectionDTO;
import com.example.mediaApp.model.dto.FriendRequestDTO;
import com.example.mediaApp.service.AppUserService;
import com.example.mediaApp.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class FriendRequestController {

    private final FriendRequestService service;
    private final AppUserService userService;
    private final AppFriendUserConverter appFriendUserConverter;
    private final FriendRequestConverter friendRequestConverter;
    private final FriendConnectionConverter friendConnectionConverter;

    @GetMapping()
    public ResponseEntity<List<FriendRequestDTO>> getAll(){
        return ResponseEntity.ok(service.getAll().stream()
                .map(friendRequestConverter::convertFromEntityToDTO)
                .toList());
    }

    @PostMapping
    public ResponseEntity<FriendRequestDTO> create(@RequestBody Emails ids){
        return ResponseEntity.ok(service.create(ids)
                .map(friendRequestConverter::convertFromEntityToDTO)
                .orElse(new FriendRequestDTO()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestDTO>> getReceivedRequests(@RequestParam String email){
        return ResponseEntity.ok(service.getReceivedRequests(email).stream()
                .map(friendRequestConverter::convertFromEntityToDTO)
                .toList());
    }

    @GetMapping("/friendListWithIsFriend")
    public ResponseEntity<List<AppFriendUserDTO>> getAll(@RequestParam String email){
        return ResponseEntity.ok(userService.getAllWithIsFriend(email)
                .stream().map(entity -> appFriendUserConverter
                        .email(email)
                        .convertFromEntityToDTO(entity))
                .toList());
    }

    @PutMapping("/accept")
    public ResponseEntity<FriendConnectionDTO> acceptRequest(@RequestParam String receiverEmail, @RequestParam String senderEmail){
        return ResponseEntity.ok(service.acceptRequest(receiverEmail, senderEmail)
                .map(friendConnectionConverter::convertFromEntityToDTO)
                .orElse(new FriendConnectionDTO()));
    }

    @PutMapping("/reject")
    @ResponseStatus(value = HttpStatus.OK)
    public void rejectRequest(@RequestBody Emails emails){
        service.rejectRequest(emails);
    }

    public record Emails(String senderEmail, String receiverEmail){}
}
