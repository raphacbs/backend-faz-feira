package com.coelho.fazfeira.behavior.unit;

import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;

import java.util.Map;

public class UnitSearchAll implements UnitSearchBehavior {
    @Override
    public Page<Unit> searchPageUnit(UnitRepository unitRepository, Map<String, String> params) {
        return unitRepository.findAll(getPageable(params));
    }
}
