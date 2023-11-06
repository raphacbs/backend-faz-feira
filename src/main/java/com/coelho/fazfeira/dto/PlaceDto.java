package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDto {
    private String type;
    private List<Feature> features;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Feature {
        private String type;
        private Property properties;
    }

}
