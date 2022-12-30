package com.coelho.fazfeira.behavior;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;

import java.util.Map;

public class UnitSearchByDescriptionAndInitials implements UnitSearchBehavior{
    @Override
    public Page<Unit> searchPageUnit(UnitRepository unitRepository, Map<String, Object> params) {
        return unitRepository
                .findByDescriptionIgnoreCaseContainingAndInitialsIgnoreCaseContaining(
                        getPageable(params),
                        params.get(Params.UNIT_DESCRIPTION).toString(),
                        params.get(Params.UNIT_INITIALS).toString());
    }
}
