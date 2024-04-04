package com.example.mediaApp.controller;

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

    @GetMapping()
    public ResponseEntity<List<FriendRequestDTO>> getAll(){
        return ResponseEntity.ok(service.getAll().stream()
                .map(friendRequestEntity -> new FriendRequestDTO(
                    friendRequestEntity.getId(),
                    friendRequestEntity.getSenderUser().getId(),
                    friendRequestEntity.getSenderUser().getEmail(),
                        STR."\{friendRequestEntity.getSenderUser().getFirstName()} \{friendRequestEntity.getSenderUser().getLastName()}",
                    friendRequestEntity.getReceiverUser().getId(),
                    friendRequestEntity.getReceiverUser().getEmail()
                ))
                .toList());
    }

    @PostMapping
    public ResponseEntity<FriendRequestDTO> create(@RequestBody Emails ids){
        return ResponseEntity.ok(service.create(ids)
                .map(request -> new FriendRequestDTO(
                    request.getId(),
                    request.getSenderUser().getId(),
                    request.getSenderUser().getEmail(),
                    STR."\{request.getSenderUser().getFirstName()} \{request.getSenderUser().getLastName()}",
                    request.getReceiverUser().getId(),
                    request.getReceiverUser().getEmail()))
                .orElse(new FriendRequestDTO()));
    }

    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestDTO>> getReceivedRequests(@RequestParam String email){
        return ResponseEntity.ok(service.getReceivedRequests(email).stream()
                .map(request -> new FriendRequestDTO(
                    request.getId(),
                    request.getReceiverUser().getId(),
                    request.getReceiverUser().getEmail(),
                    STR."\{request.getSenderUser().getFirstName()} \{request.getSenderUser().getLastName()}",
                    request.getSenderUser().getId(),
                    request.getSenderUser().getEmail()
                ))
                .toList());
    }

    @GetMapping("/friendListWithIsFriend")
    public ResponseEntity<List<AppFriendUserDTO>> getAll(@RequestParam String email){
        return ResponseEntity.ok(userService.getAllWithIsFriend(email));
    }

    @PutMapping("/accept")
    public ResponseEntity<FriendConnectionDTO> acceptRequest(@RequestParam String receiverEmail, @RequestParam String senderEmail){
        return ResponseEntity.ok(service.acceptRequest(receiverEmail, senderEmail)
                .map(result -> new FriendConnectionDTO(
                        result.getUser().getEmail(),
                        result.getFriend().getEmail()
                        )).orElse(new FriendConnectionDTO()));
    }

    @PutMapping("/reject")
    @ResponseStatus(value = HttpStatus.OK)
    public void rejectRequest(@RequestBody Emails emails){
        service.rejectRequest(emails);
    }


    public record Emails(String senderEmail, String receiverEmail){}
}
