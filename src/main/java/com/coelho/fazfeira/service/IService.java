package com.coelho.fazfeira.service;


import com.coelho.fazfeira.dto.ResponseList;

import java.util.Map;
import java.util.UUID;

public interface IService<DTO, Input> {
    DTO create(Input obj);

    DTO update(Input obj);

    DTO delete(UUID id);

    ResponseList<DTO> getByParams(Map<String, Object> params);
}
