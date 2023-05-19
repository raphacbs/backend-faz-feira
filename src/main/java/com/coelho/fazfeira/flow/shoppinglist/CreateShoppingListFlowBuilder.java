package com.coelho.fazfeira.flow.shoppinglist;

import com.coelho.fazfeira.flow.Flow;
import com.coelho.fazfeira.flow.FlowFactory;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.actions.shoppinglist.ConvertEntityToDtoShoppingListHandler;
import com.coelho.fazfeira.handlers.actions.shoppinglist.ConvertInputToEntityShoppingListHandler;
import com.coelho.fazfeira.handlers.actions.shoppinglist.SaveShoppingListHandler;
import com.coelho.fazfeira.handlers.actions.shoppinglist.ValidateInputShoppingListHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateShoppingListFlowBuilder {
    private Flow flow;
    private final FlowFactory flowFactory;


    public CreateShoppingListFlowBuilder(FlowFactory flowFactory) {

        this.flowFactory = flowFactory;
    }

    public CreateShoppingListFlowBuilder create(Context context) {
        flow = flowFactory
                .start()
                .addAction(ValidateInputShoppingListHandler.class)
                .addAction(ConvertInputToEntityShoppingListHandler.class)
                .addAction(SaveShoppingListHandler.class)
                .addAction(ConvertEntityToDtoShoppingListHandler.class)
                .context(context)
                .build();

        return this;
    }

    private void run() {
        if (this.flow != null) {
            this.flow.run();
        } else {
            throw new IllegalStateException("FlowFactory has not been created. Call create() method first.");
        }
    }

    public Flow build() {
        if (this.flow != null) {
           return this.flow;
        } else {
            throw new IllegalStateException("FlowFactory has not been created. Call create() method first.");
        }
    }
}

