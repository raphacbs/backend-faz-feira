package com.coelho.fazfeira.inputs;

import com.coelho.fazfeira.dto.ProductDto;
import com.coelho.fazfeira.dto.ShoppingListDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ItemInput implements Input{
    @Pattern(
            regexp = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}",
            message = "id must be a valid UUID")
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
