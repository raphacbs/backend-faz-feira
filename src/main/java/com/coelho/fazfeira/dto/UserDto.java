package com.coelho.fazfeira.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private  UUID id;
    private  String email;
    private  String firstName;
    private  String lastName;
}
