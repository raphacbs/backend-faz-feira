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
public class ShoppingListDto implements Serializable {
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$", message = "Shopping list id with invalid format")
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
        private int quantityPlannedProduct;
        private int quantityAddedProduct;
        private double plannedTotalValue;
        private double totalValueAdded;
    }
}
