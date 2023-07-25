package com.coelho.fazfeira.handlers.actions.item;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.mapper.ItemMapper;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.coelho.fazfeira.constants.Constants.Context.*;

//@Component
public class ConvertItemWithProductInputToEntityItemHandler extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(ConvertItemWithProductInputToEntityItemHandler.class);
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;



    @Override
    protected void doHandle(Context context) {
        final ItemWithPorductInput itemWithPorductInput = context.getInput(ITEM_INPUT, ItemWithPorductInput.class);
        logger.debug("Preparing object {} conversion", itemWithPorductInput);
        final Item item = this.itemMapper.itemWithProductInputToItem(itemWithPorductInput);
        context.setEntity(ITEM, item);
        logger.info("Object converted successfully");

    }
}
