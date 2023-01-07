package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchAPI;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchByLonLat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumSupermarketSearch {
    BY_API( true, true, true, new SupermarketSearchAPI()),
    BY_LONG_LAT( true, true, false, new SupermarketSearchByLonLat());

    private final boolean latitudeParam;
    private final boolean longitudeParam;
    private final boolean searchAPIParam;
    private final SearchBehavior searchBehavior;

    public static EnumSupermarketSearch find(boolean longitudeParam, boolean latitudeParam, boolean searchAPIParam){
        for(EnumSupermarketSearch elem : values()){
            if(elem.longitudeParam == longitudeParam &&
                    elem.latitudeParam == latitudeParam &&
            elem.searchAPIParam == searchAPIParam){
                return elem;
            }
        }
        return null;
    }

}
