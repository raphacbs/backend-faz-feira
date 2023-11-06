package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest implements Serializable {
    private String code;
    @NotBlank(message = "Description is mandatory")
    private String description;
    private String brand;
    private String thumbnail;
    private UnitRequestBody unit;
}
