package com.coelho.fazfeira.behavior;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface SearchBehavior<T, R> extends com.coelho.fazfeira.behavior.Pageable {

    Page<T> searchPage(R repository, Map<String, String> params);
}
