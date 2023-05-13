package com.coelho.fazfeira.service;


import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.excepitonhandler.BusinessException;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Service<D, I> {
    D create(I obj) throws BusinessException;

    D update(I obj);

    void delete(UUID id);

    ResponseList<D> getByParams(Map<String, String> params);
    Optional<D> getById(UUID id);
}
