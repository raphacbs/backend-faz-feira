package com.coelho.fazfeira.handlers;



import com.coelho.fazfeira.dto.Dto;
import com.coelho.fazfeira.inputs.Input;
import com.coelho.fazfeira.model.IEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class BaseContext implements Context {
    private final Map<String, Input> inputs = new HashMap<>();
    private final Map<String, Dto> dtos = new HashMap<>();
    private final Map<String, IEntity> entities = new HashMap<>();
    private final Map<String, List<? extends Dto>> dtosList = new HashMap<>();
    private final Map<String, List<? extends IEntity>> entitiesList = new HashMap<>();


    @Override
    public <T extends Input> T getInput(String key, Class<T> clazz) {
        Input input = inputs.get(key);
        if (!clazz.isInstance(input)) {
//            throw new IllegalArgumentException("Invalid input type for key: " + key);
            return null;
        }
        return clazz.cast(input);
    }

    @Override
    public void setInput(String key, Input input) {
        inputs.put(key, input);
    }

    @Override
    public <T extends Dto> T getDto(String key, Class<T> clazz) {
        Dto dto = dtos.get(key);
        if (!clazz.isInstance(dto)) {
//            throw new IllegalArgumentException("Invalid dto type for key: " + key);
            return null;
        }

        return clazz.cast(dto);
    }

    @Override
    public void setDto(String key, Dto dto) {
        dtos.put(key, dto);
    }

    @Override
    public <T extends IEntity> T getEntity(String key, Class<T> clazz) {
        IEntity entity = entities.get(key);
        if (!clazz.isInstance(entity)) {
//            throw new IllegalArgumentException("Invalid entity type for key: " + key);
            return null;
        }
        return clazz.cast(entity);
    }

    @Override
    public void setEntity(String key, IEntity entity) {
        entities.put(key, entity);
    }

    @Override
    public <T extends Dto> List<T> getDtos(String key, Class<T> clazz) {
        List<? extends Dto> dtos = dtosList.get(key);
        if (dtos == null) {
            return null;
        }
        List<T> result = new ArrayList<>();
        for (Dto dto : dtos) {
            if (clazz.isInstance(dto)) {
                result.add(clazz.cast(dto));
            }
        }
        return result;
    }


    @Override
    public void setDtos(String key, List<? extends Dto> dtos) {
        dtosList.put(key, dtos);
    }


    @Override
    public <T extends IEntity> List<T> getEntities(String key, Class<T> clazz) {
        List<? extends IEntity> entityList = entitiesList.get(key);
        if (entityList == null) {
            return null;
        }
        List<T> result = new ArrayList<>();
        for (IEntity entity : entityList) {
            if (clazz.isInstance(entity)) {
                result.add(clazz.cast(entity));
            }
        }
        return result;
    }


    @Override
    public void setEntities(String key, List<? extends IEntity> entities) {
        entitiesList.put(key, entities);
    }
}
