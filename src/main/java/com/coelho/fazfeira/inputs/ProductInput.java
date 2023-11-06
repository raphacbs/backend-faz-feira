package com.coelho.fazfeira.inputs;

import com.coelho.fazfeira.dto.UnitDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInput {
    @NotNull(
            message = "product code is required")
    private String code;
    @NotNull(message = "description is required ")
    private String description;
    private String brand;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    @Valid
    private UnitDto unit;
}
