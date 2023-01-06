package com.coelho.fazfeira.behavior;

import com.coelho.fazfeira.constants.Params;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;
import java.util.Optional;

public interface SearchBehavior<T, R>{
    default Pageable getPageable(Map<String, Object> params){
        String sortDir = Optional.ofNullable(params.get(Params.SORT_DIR)).orElse("desc").toString();
        String sortBy = Optional.ofNullable(params.get(Params.SORT_BY)).orElse(Params.DESCRIPTION).toString();
        int pageNo = (int) Optional.ofNullable(params.get(Params.NO_PAGE)).orElse(0);
        int pageSize = (int) Optional.ofNullable(params.get(Params.PAGE_SIZE)).orElse(10);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of((pageNo == 0 ? 0 : pageNo -1 ), pageSize, sort);
    }
    Page<T> searchPage(R repository, Map<String, Object> params);
}
