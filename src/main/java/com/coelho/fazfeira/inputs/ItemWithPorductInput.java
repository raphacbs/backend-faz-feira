package com.coelho.fazfeira.inputs;

import com.coelho.fazfeira.dto.ProductDto;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.validation.ValidUUID;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ItemWithPorductInput implements Input{
    @ValidUUID(message = "id must be a valid UUID", nullable = true)
    private String id;
    private String note;
    @NotNull(message = "quantity is mandatory")
    private Integer quantity;
    @NotNull(message = "price is mandatory")
    private Double price;
    @NotNull(message = "perUnit is mandatory")
    private Double perUnit;
    private boolean isAdded;
    @NotNull(message = "Product is mandatory")
    @Valid
    private ProductInput product;
    @NotNull(message = "Shopping List is mandatory")
    private ShoppingListDto shoppingList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NotNull(message = "unit is mandatory")
    private UnitDto unit;
}
