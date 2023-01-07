package com.coelho.fazfeira.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "supermarket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String region;
    @Column
    private String state;
    @Column(name = "state_code")
    private String stateCode;
    @Column
    private String city;
    @Column
    private String municipality;
    @Column
    private String postcode;
    @Column
    private String district;
    @Column
    private String neighbourhood;
    @Column
    private String suburb;
    @Column
    private String street;
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private Double latitude;
    @Column
    private String address;
    @Column
    private String placeId;
}