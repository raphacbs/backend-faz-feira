package com.coelho.fazfeira.behavior;

import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;

import java.util.Map;

public class UnitSearchAll implements UnitSearchBehavior{
    @Override
    public Page<Unit> search(UnitRepository unitRepository, Map<String, Object> params) {
        Page<Unit> unitPage = unitRepository.findAll(getPageable(params));
        return unitPage;
    }
}
