package com.coelho.fazfeira.handlers.actions.supermarket;

import com.coelho.fazfeira.excepitonhandler.BusinessException;
import com.coelho.fazfeira.handlers.AbstractHandler;
import com.coelho.fazfeira.handlers.Context;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.repository.SupermarketRepository;
import com.coelho.fazfeira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.coelho.fazfeira.base.BusinessCode.SupermarketCode.SUPERMARKET_NOT_EXIST;
import static com.coelho.fazfeira.constants.Constants.Context.SUPERMARKET;

@Component
public class GetSupermarketByIdHandler extends AbstractHandler {

    @Autowired
    private SupermarketRepository supermarketRepository;
    @Autowired
    private UserService userService;


    @Override
    protected void doHandle(Context context) {
        final Supermarket supermarket = context.getEntity(SUPERMARKET, Supermarket.class);
        final UUID loggedUserId = userService.getLoggedUserId();

        final Supermarket supermarketFounded = this.supermarketRepository.findById(supermarket.getId()).orElseThrow(
                () -> new BusinessException(SUPERMARKET_NOT_EXIST)
        );

        context.setEntity(SUPERMARKET, supermarketFounded);
    }
}
