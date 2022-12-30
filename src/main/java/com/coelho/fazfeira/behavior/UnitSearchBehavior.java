package com.coelho.fazfeira.behavior;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public interface UnitSearchBehavior {
    default Pageable getPageable(Map<String, Object> params){
        String sortDir = Optional.ofNullable(params.get(Params.SORT_DIR)).orElse("desc").toString();
        String sortBy = Optional.ofNullable(params.get(Params.SORT_BY)).orElse(Params.UNIT_DESCRIPTION).toString();
        int pageNo = (int) Optional.ofNullable(params.get(Params.NO_PAGE)).orElse(0);
        int pageSize = (int) Optional.ofNullable(params.get(Params.PAGE_SIZE)).orElse(10);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of((pageNo == 0 ? 0 : pageNo -1 ), pageSize, sort);
    }
    Page<Unit> searchPageUnit(UnitRepository unitRepository, Map<String, Object> params);
}
