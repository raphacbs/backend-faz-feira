package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {
    private String code;
    private String description;
    private String brand;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private UnitDto unit;
}
