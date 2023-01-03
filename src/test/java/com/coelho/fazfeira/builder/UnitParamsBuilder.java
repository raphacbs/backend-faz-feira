package com.coelho.fazfeira.builder;

import com.coelho.fazfeira.constants.Params;
import lombok.Builder;

import java.util.Map;

@Builder
public class UnitParamsBuilder {

    private String initials;
    private String description;

    public Map<String, Object> create(){
        Map<String, Object> params =  Params.getDefaultParams();
        params.put(Params.UNIT_INITIALS, this.initials);
        params.put(Params.UNIT_DESCRIPTION, this.description);
        return params;
    }
}
