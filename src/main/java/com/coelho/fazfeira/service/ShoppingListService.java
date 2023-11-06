package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.Pageable;
import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.enums.EnumShoppingListSearch;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.excepitonhandler.EntityNotExistException;
import com.coelho.fazfeira.flow.shoppinglist.CreateShoppingListFlowBuilder;
import com.coelho.fazfeira.flow.shoppinglist.UpdateShoppingListFlowBuilder;
import com.coelho.fazfeira.handlers.DefaultContext;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.mapper.ShoppingListMapper;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.util.Converts;
import com.coelho.fazfeira.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.*;
import static com.coelho.fazfeira.util.Nullables.isNotNull;

@org.springframework.stereotype.Service
public class ShoppingListService implements Service<ShoppingListDto, ShoppingListInput>, Pageable {

    private final Logger logger = LoggerFactory.getLogger(ShoppingListService.class);
    private final ShoppingListMapper shoppingListMapper = ShoppingListMapper.INSTANCE;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private InputValidator<ShoppingListInput> inputValidator;

    @Autowired
    private ItemService itemService;

    @Autowired
    private  UserService userService;
    @Autowired
    private CreateShoppingListFlowBuilder createShoppingListFlowBuilder;

    @Autowired
    private UpdateShoppingListFlowBuilder updateShoppingListFlowBuilder;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingListDto create(ShoppingListInput obj) {
        DefaultContext context = DefaultContext.builder().build();
        context.setShoppingListInput(obj);
        createShoppingListFlowBuilder.create(context).build().run();
        return context.getShoppingListDto();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingListDto update(ShoppingListInput obj) {
        DefaultContext context = DefaultContext.builder().build();
        context.setShoppingListInput(obj);
        updateShoppingListFlowBuilder.create(context).build().run();
        return context.getShoppingListDto();
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public ResponseList<ShoppingListDto> getByParams(Map<String, String> params) {

        params.put(USER_ID, userService.getLoggedUserId().toString());

        EnumShoppingListSearch search = EnumShoppingListSearch.find(isNotNull(params.get(DESCRIPTION)),
                isNotNull(params.get(USER_ID)),
                isNotNull(params.get(SHOPPING_LIST_STATUS)));

        final SearchBehavior searchBehavior = search.getSearchBehavior();
        Page<ShoppingListDto> page = searchBehavior.searchPage(this.shoppingListRepository, params);

        return shoppingListMapper.pageSupermarketToResponseList(searchBehavior.searchPage(this.shoppingListRepository, params),
                params);
    }

    @Override
    public Optional<ShoppingListDto> getById(UUID id) {
        ShoppingListInput shoppingListInput = ShoppingListInput.builder()
                .id(Converts.asString(id))
                .build();

        DefaultContext context = DefaultContext.builder().build();
        context.setShoppingListInput(shoppingListInput);


        final Optional<ShoppingList> shoppingListOptional = this.shoppingListRepository.findById(id);
        if(shoppingListOptional.isEmpty()){
            return Optional.empty();
        }else{
            final ShoppingListDto shoppingListDto = this.shoppingListMapper
                    .shoppingListToShoppingListDto(shoppingListOptional.get());
            ShoppingListDto.ItemsInfo itemsInfo = new ShoppingListDto.ItemsInfo();
            final Set<Item> items = shoppingListOptional.get().getItems();
            double plannedTotalValue = BigDecimal.valueOf(items.stream().mapToDouble(Item::getPrice).sum())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            double totalValueAdded = BigDecimal.valueOf(items.stream().mapToDouble(item ->
                            item.isAdded() ? item.getPrice() : 0).sum())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            double quantityPlannedProduct = items.stream().mapToDouble(Item::getQuantity).sum();
            double quantityAddedProduct = items.stream().mapToDouble(item -> item.isAdded() ? item.getQuantity() : 0).sum();

            itemsInfo.setPlannedTotalValue(plannedTotalValue);
            itemsInfo.setQuantityPlannedProduct(quantityPlannedProduct);
            itemsInfo.setTotalValueAdded(totalValueAdded);
            itemsInfo.setQuantityAddedProduct(quantityAddedProduct);
            shoppingListDto.setItemsInfo(itemsInfo);

            return Optional.of(shoppingListDto);
        }
    }

    private ShoppingList validateAndConvert(ShoppingListInput shoppingListInput) {
        inputValidator.validate(shoppingListInput);
        logger.debug("Preparing object conversion UnitRequestBody to Unit. {}", shoppingListInput);
        ShoppingList shoppingList =  this.shoppingListMapper.shoppingListRequestToShoppingList(shoppingListInput);
        logger.info("Object converted successfully");
        return shoppingList;
    }


}
