package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListDto implements Serializable {
    private UUID id;
    private String description;
    private UUID supermarketId;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private boolean isOpen;
    private ItemsInfo itemsInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ItemsInfo {
        private int quantityPlannedProduct;
        private int quantityAddedProduct;
        private double plannedTotalValue;
        private double totalValueAdded;
    }
}
