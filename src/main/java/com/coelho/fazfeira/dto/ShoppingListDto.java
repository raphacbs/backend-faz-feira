package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListDto implements Serializable, Dto {
    private UUID id;
    private String description;
    private UUID supermarketId;
    private String supermarketName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private ItemsInfo itemsInfo;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ItemsInfo {
        private double quantityPlannedProduct;
        private double quantityAddedProduct;
        private double plannedTotalValue;
        private double totalValueAdded;
    }
}
