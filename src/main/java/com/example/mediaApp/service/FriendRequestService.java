package com.example.mediaApp.service;

import com.example.mediaApp.controller.FriendRequestController;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendConnectionEntity;
import com.example.mediaApp.model.entity.FriendRequestEntity;
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
    private final AppUserService userService;
    private final FriendConnectionService connectionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendRequestService.class);

    public List<FriendRequestEntity> getAll(){
        return repository.findAll();
    }

    public Optional<FriendRequestEntity> create(FriendRequestController.Emails emails) {
        Optional<AppUserEntity> maybeReceiver = userService.getEntityByEmail(emails.receiverEmail());
        Optional<AppUserEntity> maybeSender = userService.getEntityByEmail(emails.senderEmail());
        if(maybeReceiver.isEmpty()){
            LOGGER.warn("Cannot construct friend request as receiver user with email \"{}\" does not exist.", emails.receiverEmail());
            return Optional.empty();
        }
        if(maybeSender.isEmpty()){
            LOGGER.warn("Cannot construct friend request as sender user with email \"{}\" does not exist.", emails.receiverEmail());
            return Optional.empty();
        }
        AppUserEntity receiver = maybeReceiver.get();
        AppUserEntity sender = maybeSender.get();
        FriendRequestEntity request = new FriendRequestEntity();
        request.setReceiverUser(receiver);
        request.setSenderUser(sender);
        repository.save(request);


        receiver.getFriendRequests().add(request);
        userService.changeUser(receiver.getId(), receiver);



        LOGGER.info("Saving succeeded.");

        return Optional.of(request);
    }

    public List<FriendRequestEntity> getReceivedRequests(String email) {
        Optional<AppUserEntity> userMaybe = userService.getEntityByEmail(email);
        if(userMaybe.isEmpty()){
            LOGGER.warn("No such user with email: {}", email);
            return List.of();
        }
        AppUserEntity user = userMaybe.get();
        return user.getFriendRequests();
    }

    public List<FriendRequestEntity> getFriendRequestByTwoEmails(FriendRequestController.Emails emails){
        return repository.findAll().stream().filter(request ->
                (request.getSenderUser().getEmail().equals(emails.senderEmail()) && request.getReceiverUser().getEmail().equals(emails.receiverEmail()))
                || (request.getSenderUser().getEmail().equals(emails.receiverEmail()) && request.getReceiverUser().getEmail().equals(emails.senderEmail()))
        ).toList();
    }

    public Optional<FriendConnectionEntity> acceptRequest(String receiverEmail, String senderEmail) {
        if(receiverEmail.equals(senderEmail)){
            LOGGER.error("Emails cannot be the same. Email:{} ", receiverEmail);
            throw new IllegalStateException("Emails are the same.");
        }
        Optional<AppUserEntity> maybeReceiver = userService.getEntityByEmail(receiverEmail);
        Optional<AppUserEntity> maybeSender = userService.getEntityByEmail(senderEmail);
        if(maybeSender.isEmpty() || maybeReceiver.isEmpty()){
            LOGGER.warn("Sender or Receiver is empty");
            return Optional.empty();
        }

        AppUserEntity receiver = maybeReceiver.get();
        AppUserEntity sender = maybeSender.get();

        //creating connection entity for receiver end
        FriendConnectionEntity connectionReceiver = new FriendConnectionEntity();
        connectionReceiver.setUser(receiver);
        connectionReceiver.setFriend(sender);

        //creating connection entity for sender end
        FriendConnectionEntity connectionSender = new FriendConnectionEntity();
        connectionSender.setUser(sender);
        connectionSender.setFriend(receiver);

        //add friend connection to both ends
        receiver.getFriendConnections().add(connectionReceiver);
        sender.getFriendConnections().add(connectionSender);

        //remove friend request for both ends
        receiver.getFriendRequests().removeIf(request ->
                    request.getReceiverUser().getEmail().equals(receiverEmail)
                            && request.getSenderUser().getEmail().equals(senderEmail)
            );
        sender.getFriendRequests().removeIf(request ->
                    request.getReceiverUser().getEmail().equals(receiverEmail)
                            && request.getSenderUser().getEmail().equals(senderEmail)
            );

        FriendRequestController.Emails emails = new FriendRequestController.Emails(senderEmail, receiverEmail);
        repository.deleteAll(getFriendRequestByTwoEmails(emails));
        connectionService.create(connectionSender);
        connectionService.create(connectionReceiver);
        userService.changeUser(receiver.getId(), receiver);
        userService.changeUser(sender.getId(), sender);
        LOGGER.info("Friend request accepted: receiver = {}, sender = {}", receiverEmail, senderEmail);

        return Optional.of(connectionSender);
    }


    public void rejectRequest(FriendRequestController.Emails emails) {
        if(emails.receiverEmail().equals(emails.senderEmail())){
            LOGGER.error("Emails cannot be the same. Email:{} ", emails.receiverEmail());
            throw new IllegalStateException("Emails are the same.");
        }
        Optional<AppUserEntity> maybeSender = userService.getEntityByEmail(emails.senderEmail());
        Optional<AppUserEntity> maybeReceiver = userService.getEntityByEmail(emails.receiverEmail());

        if(maybeReceiver.isEmpty() || maybeSender.isEmpty()){
            LOGGER.warn("Sender or Receiver is empty");
            return;
        }

        AppUserEntity sender = maybeSender.get();
        AppUserEntity receiver = maybeReceiver.get();

        //remove friend request for both ends
        receiver.getFriendRequests().removeIf(request ->
                request.getReceiverUser().getEmail().equals(emails.receiverEmail())
                        && request.getSenderUser().getEmail().equals(emails.senderEmail())
        );
        sender.getFriendRequests().removeIf(request ->
                request.getReceiverUser().getEmail().equals(emails.receiverEmail())
                        && request.getSenderUser().getEmail().equals(emails.senderEmail())
        );

        repository.deleteAll(getFriendRequestByTwoEmails(emails));
        userService.changeUser(sender.getId(), sender);
        userService.changeUser(receiver.getId(), receiver);

        LOGGER.info("Request rejected. Receiver: {}, sender:{}", emails.receiverEmail(), emails.senderEmail());
    }
}
