package com.coelho.fazfeira.behavior.enums;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchAPI;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchByLonLat;
import com.coelho.fazfeira.behavior.supermarket.SupermarketSearchByName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumSupermarketSearch {
    BY_API(true, true, false,true, new SupermarketSearchAPI()),
    BY_NAME(true, true, true,false, new SupermarketSearchByName()),
    BY_LONG_LAT(true, true, false, false, new SupermarketSearchByLonLat());

    private final boolean latitudeParam;
    private final boolean longitudeParam;
    private final boolean nameParam;
    private final boolean searchAPIParam;
    private final SearchBehavior searchBehavior;

    public static EnumSupermarketSearch find(boolean longitudeParam,
                                             boolean latitudeParam,
                                             boolean nameParam,
                                             boolean searchAPIParam) {
        for (EnumSupermarketSearch elem : values()) {
            if (elem.longitudeParam == longitudeParam &&
                    elem.latitudeParam == latitudeParam &&
                    elem.nameParam == nameParam &&
                    elem.searchAPIParam == searchAPIParam) {
                return elem;
            }
        }
        return null;
    }

}
