package com.example.mediaApp.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Address")
@Table(name = "address")
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country", nullable = false)
    @ToString.Include
    private String country;

    @Column(name = "city", nullable = false)
    @ToString.Include
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "street", nullable = false)
    @ToString.Include
    private String street;

    @Column(name = "house_num", nullable = false)
    private String houseNum;
}
