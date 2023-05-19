package com.coelho.fazfeira.flow.shoppinglist;

import com.coelho.fazfeira.flow.Flow;
import com.coelho.fazfeira.flow.FlowFactory;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.actions.shoppinglist.*;
import org.springframework.stereotype.Component;

@Component
public class UpdateShoppingListFlowBuilder {
    private Flow flow;
    private final FlowFactory flowFactory;


    public UpdateShoppingListFlowBuilder(FlowFactory flowFactory) {

        this.flowFactory = flowFactory;
    }

    public UpdateShoppingListFlowBuilder create(Context context) {
        flow = flowFactory
                .start()
                .addAction(ValidateInputShoppingListHandler.class)
                .addAction(ConvertInputToEntityShoppingListHandler.class)
                .addAction(GetShoppingListByIdHandler.class)
                .addAction(UpdateShoppingListFromInputHandler.class)
                .addAction(SaveShoppingListHandler.class)
                .addAction(ConvertEntityToDtoShoppingListHandler.class)
                .context(context)
                .build();

        return this;
    }

    public void run() {
        if (this.flow != null) {
            this.flow.run();
        } else {
            throw new IllegalStateException("FlowFactory has not been created. Call create() method first.");
        }
    }

    public Flow build() {
        return this.flow;
    }
}

