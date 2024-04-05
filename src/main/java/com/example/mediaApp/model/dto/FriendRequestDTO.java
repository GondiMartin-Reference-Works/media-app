package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendRequestDTO {
    private long id;
    private long senderId;
    private String senderEmail;
    private String senderName;
    private long receiverId;
    private String receiverEmail;
}
