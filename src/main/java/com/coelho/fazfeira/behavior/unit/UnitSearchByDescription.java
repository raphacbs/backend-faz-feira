package com.coelho.fazfeira.behavior.unit;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UnitSearchByDescription implements UnitSearchBehavior{


    @Override
    public Page<Unit> searchPageUnit(UnitRepository unitRepository, Map<String, Object> params) {
        return unitRepository.findByDescriptionIgnoreCaseContaining(getPageable(params),
                params.get(Params.UNIT_DESCRIPTION).toString());
    }
}
