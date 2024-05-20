package com.example.mediaApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddressDTO {
    private Long id;
    private String country;
    private String city;
    private String zipCode;
    private String street;
    private String houseNum;
}