package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnitDto implements Serializable {
    private String id;
    private String description;
    private String initials;
}
