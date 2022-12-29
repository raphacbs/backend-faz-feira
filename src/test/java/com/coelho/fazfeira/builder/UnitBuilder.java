package com.coelho.fazfeira.builder;

import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.model.Unit;

import java.util.Optional;
import java.util.UUID;

public class UnitBuilder {
    public static Unit createValid() {
        return Unit.builder().description("QUILOGRAMA").initials("KG").id(UUID.randomUUID()).build();
    }

    public static Unit createFromUnitRequestBody(UnitRequestBody unitRequestBody) {
        if (null != unitRequestBody) {
            return Unit.builder()
                    .description(unitRequestBody.getDescription())
                    .initials(unitRequestBody.getInitials())
                    .id(UUID.randomUUID()).build();
        }else {
            return null;
        }
    }

    public static Unit createFromUnitRequestBodyWithInitialsEmpty(UnitRequestBody unitRequestBody) {
        if (null != unitRequestBody) {
            return Unit.builder()
                    .description("")
                    .initials(unitRequestBody.getInitials())
                    .id(UUID.randomUUID()).build();
        }else {
            return null;
        }
    }
    public static Unit createFromUnitRequestBodyWithDescriptionEmpty(UnitRequestBody unitRequestBody) {
        if (null != unitRequestBody) {
            return Unit.builder()
                    .description(unitRequestBody.getDescription())
                    .initials("")
                    .id(UUID.randomUUID()).build();
        }else {
            return null;
        }
    }
}
