package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.product.ProductNoSearch;
import com.coelho.fazfeira.behavior.product.ProductSearchByCode;
import com.coelho.fazfeira.behavior.product.ProductSearchByDescription;
import com.coelho.fazfeira.behavior.product.ProductSearchCosmoDesc;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumProductSearchBehavior {
    BY_DESCRIPTION( true, false,false, new ProductSearchByDescription()),
    BY_COSMO(true, true, false, new ProductSearchCosmoDesc()),
    BY_CODE(false, false, true, new ProductSearchByCode()),
    BY_DESCRIPTION_CODE_COSMO(true, true, true, new ProductSearchByCode()),
    NO_SEARCH(false, false, false, new ProductNoSearch()),
    BY_DESCRIPTION_CODE(true, false, true, new ProductSearchByCode());

    private final boolean descriptionParam;
    private final boolean cosmoSearchParam;
    private final boolean codeParam;
    private final SearchBehavior unitSearchBehavior;

    public static EnumProductSearchBehavior find(boolean descriptionParam, boolean cosmoSearchParam, boolean codeParam){
        for(EnumProductSearchBehavior elem : values()){
            if(elem.descriptionParam == descriptionParam &&
            elem.cosmoSearchParam == cosmoSearchParam &&
                    elem.codeParam == codeParam){
                return elem;
            }
        }
        return null;
    }

}
