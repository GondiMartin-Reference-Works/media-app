package com.example.mediaApp.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private Long id;
    private String country;
    private String city;
    private String zipCode;
    private String street;
    private String houseNum;
    private AppUser user;
}
