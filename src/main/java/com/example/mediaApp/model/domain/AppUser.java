package com.example.mediaApp.model.domain;

import com.example.mediaApp.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String password;
    private Role role;
    private byte[] profilePicture;
    private List<Address> addressList;
}
