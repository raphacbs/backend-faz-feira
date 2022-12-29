package com.coelho.fazfeira.builder;

import com.coelho.fazfeira.dto.UnitRequestBody;

import java.util.UUID;

public class UnitRequestBodyBuilder {
    public static UnitRequestBody createValid(){
        return UnitRequestBody.builder().description("QUILOGRAMA").initials("KG").build();
    }

    public static UnitRequestBody createInitialsAlreadyExist(){
        return UnitRequestBody.builder().id(UUID.randomUUID().toString()).description("TEST").initials("EXIST").build();
    }
    public static UnitRequestBody createDescriptionAlreadyExist(){
        return UnitRequestBody.builder().id(UUID.randomUUID().toString()).description("EXIST").initials("TEST").build();
    }

    public static UnitRequestBody createWithoutInitials(){
        return UnitRequestBody.builder().description("QUILOGRAMA").initials(null).build();
    }
    public static UnitRequestBody createWithoutDescription(){
        return UnitRequestBody.builder().description(null).initials("KG").build();
    }
    public static UnitRequestBody createNotExist(){
        return UnitRequestBody.builder().id(UUID.randomUUID().toString()).description("null").initials("null").build();
    }
}
