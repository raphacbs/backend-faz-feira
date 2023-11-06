package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
            message = "product code with invalid format")
    private String code;
    private String description;
    private String brand;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private UnitDto unit;
    private List<PriceHistoryDto> priceHistories;
}
