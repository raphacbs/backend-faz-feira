package com.coelho.fazfeira.handlers.actions.pricehistory;

import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.PriceHistory;
import com.coelho.fazfeira.repository.ItemRepository;
import com.coelho.fazfeira.repository.PriceHistoryRepository;
import com.coelho.fazfeira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.coelho.fazfeira.constants.Constants.Context.ITEM;
import static com.coelho.fazfeira.constants.Constants.Context.PRICE_HISTORY;

@Component
public class SavePriceHistoryHandler extends AbstractHandler {

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Override
    protected void doHandle(Context context) {
        final PriceHistory priceHistory = context.getEntity(PRICE_HISTORY, PriceHistory.class);
        if (priceHistory.getId() == null) {
            priceHistory.setCreatedAt(LocalDateTime.now());
        }
        priceHistory.setUpdatedAt(LocalDateTime.now());
        this.priceHistoryRepository.save(priceHistory);
    }
}
