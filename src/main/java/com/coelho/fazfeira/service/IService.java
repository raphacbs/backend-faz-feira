package com.coelho.fazfeira.service;


import com.coelho.fazfeira.dto.ResponseList;

import java.util.Map;
import java.util.UUID;

public interface IService<D, I> {
    D create(I obj);

    D update(I obj);

    D delete(UUID id);

    ResponseList<D> getByParams(Map<String, Object> params);
}
