package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto implements Serializable {
    private UUID id;
    private String note;
    @NotNull(message = "quantity is mandatory")
    private Integer quantity;
    @NotNull(message = "price is mandatory")
    private Double price;
    @NotNull(message = "perUnit is mandatory")
    private Double perUnit;
    private boolean isAdded;
    @NotNull(message = "Product is mandatory")
    private ProductDto product;
    @NotNull(message = "Shopping List is mandatory")
    private ShoppingListDto shoppingList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
