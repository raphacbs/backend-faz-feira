package com.coelho.fazfeira.handlers.actions.item;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.validation.InputValidator;
import org.springframework.stereotype.Component;

import static com.coelho.fazfeira.constants.Constants.Context.ITEM_INPUT;

@Component
public class ValidateInputItemWithProductHandler extends AbstractHandler {

private final InputValidator<ItemWithPorductInput> inputValidator;

    public ValidateInputItemWithProductHandler(InputValidator<ItemWithPorductInput> inputValidator) {
        this.inputValidator = inputValidator;
    }

    @Override
    protected void doHandle(Context context) {
        inputValidator.validate((context.getInput(ITEM_INPUT, ItemWithPorductInput.class)));
    }
}
