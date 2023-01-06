package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.product.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumProductBuilder {
    WITH_CODE( true,  new ProductBuilder()),
    WITHOUT_CODE(false, new ProductBuilderGenerateCode())
    ;

    private final boolean codeParam;
    private final ProductBuilderBehavior productBuilderBehavior;

    public static EnumProductBuilder find(boolean codeParam){
        for(EnumProductBuilder elem : values()){
            if(elem.codeParam == codeParam){
                return elem;
            }
        }
        return null;
    }

}
