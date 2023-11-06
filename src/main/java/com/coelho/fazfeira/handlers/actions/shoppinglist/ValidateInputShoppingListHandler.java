package com.coelho.fazfeira.handlers.actions.shoppinglist;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.validation.InputValidator;
import org.springframework.stereotype.Component;

import static com.coelho.fazfeira.constants.Constants.Context.SHOPPING_LIST_INPUT;

@Component
public class ValidateInputShoppingListHandler extends AbstractHandler {

    private final InputValidator<ShoppingListInput> inputValidator;

    public ValidateInputShoppingListHandler(InputValidator<ShoppingListInput> inputValidator) {
        this.inputValidator = inputValidator;
    }

    @Override
    protected void doHandle(Context context) {
        inputValidator.validate(context.getInput(SHOPPING_LIST_INPUT, ShoppingListInput.class));
    }
}
