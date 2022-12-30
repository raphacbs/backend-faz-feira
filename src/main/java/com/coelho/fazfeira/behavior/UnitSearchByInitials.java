package com.coelho.fazfeira.behavior;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class UnitSearchByInitials implements UnitSearchBehavior{

    @Override
    public Page<Unit> searchPageUnit(UnitRepository unitRepository, Map<String, Object> params) {
        return unitRepository.findByInitialsIgnoreCaseContaining(getPageable(params),
                params.get(Params.UNIT_INITIALS).toString());
    }
}
