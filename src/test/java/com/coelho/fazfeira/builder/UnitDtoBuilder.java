package com.coelho.fazfeira.builder;

import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.github.javafaker.Faker;


public class UnitDtoBuilder {
    public  static UnitDto create(){
        Faker faker = new Faker();
        return UnitDto.builder().id(faker.internet().uuid()).description(faker.funnyName().name()).initials(faker.funnyName().name()).build();
    }

    public static UnitDto create(UnitRequestBody unitRequestBody) {
        Faker faker = new Faker();
        return UnitDto.builder().id(faker.internet().uuid()).description(unitRequestBody.getDescription()).initials(unitRequestBody.getInitials()).build();
    }
}
