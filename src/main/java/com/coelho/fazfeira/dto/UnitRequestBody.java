package com.coelho.fazfeira.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@ToString
public class UnitRequestBody {

    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$", message = "Id with invalid format")
    private String id;

    @NotBlank(message = "Description is mandatory")
    @Pattern(regexp="^[A-Za-z]*$",message = "Description with invalid format")
    private String description;

    @Pattern(regexp="^[A-Za-z]*$",message = "Initials with invalid format")
    @NotBlank(message = "Initials is mandatory")
    @NotNull
    @Size(max = 3, min = 1)
    private String initials;
}
