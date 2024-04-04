package com.example.mediaApp.service;

import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendConnectionEntity;
import com.example.mediaApp.repository.FriendConnectionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendConnectionService {
    private final FriendConnectionRepository repository;
    private final AppUserService userService;

    private static final Logger LOGGER = LogManager.getLogger(FriendConnectionService.class);

    public List<FriendConnectionEntity> getAllForEmail(String email){
        Optional<AppUserEntity> maybeUser = userService.getEntityByEmail(email);
        if(maybeUser.isEmpty()){
            throw new IllegalStateException(STR."No user is present: \{email}");
        }
        AppUserEntity user = maybeUser.get();
        return user.getFriendConnections();
    }

    public Optional<FriendConnectionEntity> create(FriendConnectionEntity entity){
        return Optional.of(repository.save(entity));
    }

    public Optional<FriendConnectionEntity> delete(String loggedInUserEmail, String toBeDeletedUserEmail){
         if(loggedInUserEmail.equals(toBeDeletedUserEmail)){
             throw new IllegalStateException(STR."Emails cannot be the same! Email: \{loggedInUserEmail}");
         }
         Optional<AppUserEntity> maybeLoggedInUser = userService.getEntityByEmail(loggedInUserEmail);
         Optional<AppUserEntity> maybeToBeDeletedUser = userService.getEntityByEmail(toBeDeletedUserEmail);

         if(maybeLoggedInUser.isEmpty() || maybeToBeDeletedUser.isEmpty()){
             LOGGER.error(STR."\{loggedInUserEmail} or \{toBeDeletedUserEmail} does not exist.");
             throw new IllegalStateException(STR."\{loggedInUserEmail} or \{toBeDeletedUserEmail} does not exist.");
         }

         AppUserEntity loggedInUser = maybeLoggedInUser.get();
         AppUserEntity toBeDeletedUser = maybeToBeDeletedUser.get();

         loggedInUser.getFriendConnections().removeIf(connection ->
                 connection.getUser().getId().equals(loggedInUser.getId())
                         && connection.getFriend().getId().equals(toBeDeletedUser.getId()));

         toBeDeletedUser.getFriendConnections().removeIf(connection ->
                 connection.getUser().getId().equals(toBeDeletedUser.getId())
                         && connection.getFriend().getId().equals(loggedInUser.getId()));

         userService.changeUser(loggedInUser.getId(), loggedInUser);
         userService.changeUser(toBeDeletedUser.getId(), toBeDeletedUser);
         List<FriendConnectionEntity> connectionList = getAllByEmails(loggedInUserEmail, toBeDeletedUserEmail);
         repository.deleteAll(connectionList);

        return Optional.ofNullable(!connectionList.isEmpty() ? connectionList.getFirst() : null);
    }

    private List<FriendConnectionEntity> getAllByEmails(String loggedInUserEmail, String toBeDeletedUserEmail){
        return repository.findAll().stream()
                .filter(connection -> connection.getUser().getEmail().equals(loggedInUserEmail)
                    && connection.getFriend().getEmail().equals(toBeDeletedUserEmail) ||
                        (connection.getUser().getEmail().equals(toBeDeletedUserEmail)
                        && connection.getFriend().getEmail().equals(loggedInUserEmail)))
                .toList();
    }

}
