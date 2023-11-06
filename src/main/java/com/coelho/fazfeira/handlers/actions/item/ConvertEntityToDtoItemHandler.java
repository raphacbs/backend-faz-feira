package com.coelho.fazfeira.handlers.actions.item;

import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.mapper.ItemMapper;
import com.coelho.fazfeira.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.coelho.fazfeira.constants.Constants.Context.*;

//@Component
public class ConvertEntityToDtoItemHandler extends AbstractHandler {

    private final Logger logger = LoggerFactory.getLogger(ConvertEntityToDtoItemHandler.class);
    private final ItemMapper itemMapper = ItemMapper.INSTANCE;



    @Override
    protected void doHandle(Context context) {
        final Item item = context.getEntity(ITEM, Item.class);
        logger.debug("Preparing object conversion");
        final ItemDto itemDto = itemMapper.itemToItemDto(item);
        context.setDto(ITEM_DTO, itemDto);
        logger.info("Object converted successfully");

    }
}
