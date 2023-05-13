package com.coelho.fazfeira.config;


import com.coelho.fazfeira.dto.TokenDto;
import com.coelho.fazfeira.dto.UserDto;

public interface JwtGenerator {

    TokenDto generateToken(UserDto user);
}
