package com.coelho.fazfeira.flow.item;


import com.coelho.fazfeira.flow.AFlowBuilder;
import com.coelho.fazfeira.flow.FlowFactory;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.handlers.actions.item.*;
import com.coelho.fazfeira.handlers.actions.pricehistory.CreatePriceHistoryHandler;
import com.coelho.fazfeira.handlers.actions.pricehistory.SavePriceHistoryHandler;
import com.coelho.fazfeira.handlers.actions.product.ConvertItemWithProductInputToEntityProductHandler;
import com.coelho.fazfeira.handlers.actions.product.SaveProductHandler;
import com.coelho.fazfeira.handlers.actions.product.ValidateProductAlreadyExistByCodeHandler;
import com.coelho.fazfeira.handlers.actions.shoppinglist.ConvertItemWithProductInputToEntityShoppingListHandler;
import com.coelho.fazfeira.handlers.actions.shoppinglist.GetShoppingListByIdHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateProductAndItemFlowBuilder extends AFlowBuilder<CreateProductAndItemFlowBuilder> {
    private final FlowFactory flowFactory;
    protected CreateProductAndItemFlowBuilder(FlowFactory flowFactory) {
        this.flowFactory = flowFactory;
    }

    public CreateProductAndItemFlowBuilder create(Context context){
        flow = flowFactory
                .start()
                .addAction(ValidateInputItemWithProductHandler.class)
                .addAction(ConvertItemWithProductInputToEntityProductHandler.class)
                .addAction(ConvertItemWithProductInputToEntityItemHandler.class)
                .addAction(ConvertItemWithProductInputToEntityShoppingListHandler.class)
                .addAction(ValidateProductAlreadyExistByCodeHandler.class)
                .addAction(GetShoppingListByIdHandler.class)
                .addAction(SaveProductHandler.class)
                .addAction(AddProductItemHandler.class)
                .addAction(SaveItemHandler.class)
                .addAction(CreatePriceHistoryHandler.class)
                .addAction(SavePriceHistoryHandler.class)
                .addAction(ConvertEntityToDtoItemHandler.class)
                .context(context)
                .build();
        return this;
    }


}
