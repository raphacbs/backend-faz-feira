package com.coelho.fazfeira.handlers;

import com.coelho.fazfeira.dto.Dto;
import com.coelho.fazfeira.inputs.Input;
import com.coelho.fazfeira.model.IEntity;

import java.util.List;

public interface Context {
    <T extends Input> T getInput(String key, Class<T> clazz);

    void setInput(String key, Input input);

    <T extends Dto> T getDto(String key, Class<T> clazz);

    void setDto(String key, Dto dto);

    <T extends IEntity> T getEntity(String key, Class<T> clazz);

    void setEntity(String key, IEntity entity);

    <T extends Dto> List<T> getDtos(String key, Class<T> clazz);

    void setDtos(String key, List<? extends Dto> dtos);

    <T extends IEntity> List<T> getEntities(String key, Class<T> clazz);

    void setEntities(String key, List<? extends IEntity> entities);
}
