package com.example.mediaApp.friendrequest;

import com.example.mediaApp.auth.AuthenticationService;
import com.example.mediaApp.auth.RegisterRequest;
import com.example.mediaApp.controller.FriendRequestController;
import com.example.mediaApp.model.entity.AppUserEntity;
import com.example.mediaApp.model.entity.FriendRequestEntity;
import com.example.mediaApp.model.entity.Role;
import com.example.mediaApp.service.AppUserService;
import com.example.mediaApp.service.FriendRequestService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor
public class FriendRequestTest {
    private static AppUserEntity sender;
    private static AppUserEntity receiver;

    private static FriendRequestEntity request;

    @Inject
    private  AppUserService userService;
    @Inject
    private  FriendRequestService requestService;
    @Inject
    private  AuthenticationService authService;

    @BeforeAll
    public static void init(){
        sender = new AppUserEntity();
        sender.setEmail("sender@gmail.com");
        sender.setFirstName("Elek");
        sender.setLastName("Teszt");
        sender.setRole(Role.USER);
        sender.setPassword("123");

        receiver = new AppUserEntity();
        receiver.setEmail("receiver@gmail.com");
        receiver.setFirstName("Áron");
        receiver.setLastName("Minden");
        receiver.setRole(Role.USER);
        receiver.setPassword("123");

//        request = new FriendRequestEntity();
//        request.setSenderUser(sender);
//        request.setReceiverUser(receiver);
    }

    @Test
    @Transactional
    void testAddRequest(){
        authService.register(new RegisterRequest(sender.getFirstName(), sender.getLastName(), sender.getEmail(), sender.getPassword()));
        authService.register(new RegisterRequest(receiver.getFirstName(), receiver.getLastName(), receiver.getEmail(), receiver.getPassword()));
        request = requestService.create(new FriendRequestController.Emails(sender.getEmail(), receiver.getEmail())).orElse(new FriendRequestEntity());

        System.out.println("");
        assertThat(userService.getEntityByEmail("sender@gmail.com")).map(AppUserEntity::getFriendRequests).isNotEmpty();
        assertThat(userService.getEntityByEmail("receiver@gmail.com")).map(AppUserEntity::getFriendRequests).isNotEmpty();
    }
}
