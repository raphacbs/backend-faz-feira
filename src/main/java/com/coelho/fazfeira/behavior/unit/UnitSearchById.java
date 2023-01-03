package com.coelho.fazfeira.behavior.unit;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.UUID;

public class UnitSearchById implements UnitSearchBehavior {
    @Override
    public Page<Unit> searchPageUnit(UnitRepository unitRepository, Map<String, Object> params) {
        return unitRepository.findById(getPageable(params),
                UUID.fromString(String.valueOf(params.get(Params.ID)))
        );
    }
}
