package com.coelho.fazfeira.handlers;

import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.model.ShoppingList;
import lombok.Builder;

import static com.coelho.fazfeira.constants.Constants.Context.*;

@Builder

public class DefaultContext extends BaseContext {
    public ShoppingList getShoppingList() {
        return this.getEntity(SHOPPING_LIST, ShoppingList.class);
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.setEntity(SHOPPING_LIST, shoppingList);
    }

    public ShoppingListInput getShoppingListInput() {
        return this.getInput(SHOPPING_LIST_INPUT, ShoppingListInput.class);
    }

    public void setShoppingListInput(ShoppingListInput shoppingListInput) {
        this.setInput(SHOPPING_LIST_INPUT, shoppingListInput);
    }

    public ShoppingListDto getShoppingListDto() {
        return this.getDto(SHOPPING_LIST_DTO, ShoppingListDto.class);
    }

    public void setShoppingListDto(ShoppingListDto shoppingListDto) {
        this.setDto(SHOPPING_LIST_DTO, shoppingListDto);
    }


}
