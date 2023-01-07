package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupermarketDto implements Serializable {
    private UUID id;
    private String name;
    private String country;
    private String region;
    private String state;
    private String stateCode;
    private String city;
    private String municipality;
    private String postcode;
    private String district;
    private String neighbourhood;
    private String suburb;
    private String street;
    private Double longitude;
    private Double latitude;
    private String address;
    private String placeId;
    private double distance;
}
