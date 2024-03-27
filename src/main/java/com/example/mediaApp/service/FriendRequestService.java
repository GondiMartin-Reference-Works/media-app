package com.example.mediaApp.service;

import com.example.mediaApp.controller.FriendRequestController;
import com.example.mediaApp.model.dto.FriendRequestDTO;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendRequestEntity;
import com.example.mediaApp.repository.AppUserRepository;
import com.example.mediaApp.repository.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRequestRepository repository;
    private final AppUserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendRequestService.class);

    public List<FriendRequestDTO> getAll(){
        return repository.findAll().stream()
                .map(friendRequestEntity -> new FriendRequestDTO(
                        friendRequestEntity.getId(),
                        friendRequestEntity.getSenderUser().getId(),
                        friendRequestEntity.getSenderUser().getEmail(),
                        friendRequestEntity.getReceiverUser().getId(),
                        friendRequestEntity.getReceiverUser().getEmail()
                )).toList();
    }

    public Optional<FriendRequestDTO> create(FriendRequestController.Emails emails) {
//        Optional<AppUserDTO> receiver = userService.getByEmail(emails.receiverEmail())
//
//
//        Optional<AppUserDTO> sender = userService.getByEmail(emails.senderEmail())
//                .map(user -> new AppUserDTO(
//                        user.getId(),
//                        user.getFirstName(),
//                        user.getLastName(),
//                        user.getEmail()
//                ));

        Optional<AppUserEntity> receiver = userRepository.findByEmail(emails.receiverEmail());
        Optional<AppUserEntity> sender = userRepository.findByEmail(emails.senderEmail());
        if(receiver.isEmpty()){
            LOGGER.warn("Cannot construct friend request as receiver user with email \"{}\" does not exist.", emails.receiverEmail());
            return Optional.empty();
        }
        if(sender.isEmpty()){
            LOGGER.warn("Cannot construct friend request as sender user with email \"{}\" does not exist.", emails.receiverEmail());
            return Optional.empty();
        }
        FriendRequestEntity request = new FriendRequestEntity();
        request.setReceiverUser(receiver.get());
        request.setSenderUser(sender.get());

        repository.save(request);
        LOGGER.info("Saving succeeded.");

        return Optional.of(new FriendRequestDTO(
                request.getId(), request.getSenderUser().getId(), request.getSenderUser().getEmail(),
                request.getReceiverUser().getId(), request.getReceiverUser().getEmail()
                )
        );
    }
}
