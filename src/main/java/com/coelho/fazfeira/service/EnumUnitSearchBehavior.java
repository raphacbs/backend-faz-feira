package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.unit.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumUnitSearchBehavior {
    BY_DESCRIPTION( false,true, new UnitSearchByDescription()),
    BY_INITIALS( true, false, new UnitSearchByInitials()),
    BY_DESCRIPTION_AND_INITIALS( true,true, new UnitSearchByDescriptionAndInitials()),
    ALL( false, false,new UnitSearchAll());

    private final boolean initialsParam;
    private final boolean descriptionParam;
    private final UnitSearchBehavior unitSearchBehavior;

    public static EnumUnitSearchBehavior find(boolean initialsParam, boolean descriptionParam){
        for(EnumUnitSearchBehavior elem : values()){
            if(elem.descriptionParam == descriptionParam &&
                    elem.initialsParam == initialsParam){
                return elem;
            }
        }
        return null;
    }

}
