package com.coelho.fazfeira.handlers;

import com.coelho.fazfeira.dto.Dto;
import com.coelho.fazfeira.inputs.Input;
import com.coelho.fazfeira.inputs.ItemInput;
import com.coelho.fazfeira.model.IEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemContext implements Context{
    private ItemInput itemInput;

    @Override
    public <T extends Input> T getInput(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public void setInput(String key, Input input) {

    }

    @Override
    public <T extends Dto> T getDto(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public void setDto(String key, Dto dto) {

    }

    @Override
    public <T extends IEntity> T getEntity(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public void setEntity(String key, IEntity entity) {

    }

    @Override
    public <T extends Dto> List<T> getDtos(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public void setDtos(String key, List<? extends Dto> dtos) {

    }

    @Override
    public <T extends IEntity> List<T> getEntities(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public void setEntities(String key, List<? extends IEntity> entities) {

    }
}
