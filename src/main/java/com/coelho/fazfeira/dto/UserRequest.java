package com.coelho.fazfeira.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements Serializable {
    private String email;
    private String password;

}
