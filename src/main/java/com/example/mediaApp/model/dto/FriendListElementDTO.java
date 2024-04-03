package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendListElementDTO {
    private String email;
    private String firstName;
    private String lastName;
}
