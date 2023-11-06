package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceHistoryDto implements Serializable {
    private UUID id;
    @NotNull(message = "price must not be null")
    @DecimalMin(value = "0.1", message = "Please Enter a valid price Amount")
    private Double price;
    @NotNull(message = "product must not be null")
    private ProductResponse product;
    @NotNull(message = "supermarket must not be null")
    private SupermarketResponse supermarket;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductResponse implements Serializable {
        @NotNull(message = "product code must not be null")
        @NotBlank(message = "product code must not be blank")
        private String code;
        private String description;
        private String brand;
        private String thumbnail;
        private LocalDateTime createdAt;
        private LocalDateTime updateAt;
        private UnitDto unit;
        private ShoppingListDto shoppingList;
        private ItemDto item;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SupermarketResponse implements Serializable {
        @NotNull(message = "supermarket id must not be null")
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
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PriceDifference implements Serializable {
        private double diff;

    }
}
