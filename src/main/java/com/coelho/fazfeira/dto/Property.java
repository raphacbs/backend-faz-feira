package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    private String name;
    private String country;
    private String country_code;
    private String region;
    private String state;
    private String state_district;
    private String county;
    private String city;
    private String municipality;
    private String postcode;
    private String district;
    private String suburb;
    private String street;
    private String housenumber;
    private double lon;
    private double lat;
    private String state_code;
    private String formatted;
    private String place_id;
}