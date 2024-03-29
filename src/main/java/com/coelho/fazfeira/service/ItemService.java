package com.coelho.fazfeira.service;

import com.coelho.fazfeira.base.BusinessCode;
import com.coelho.fazfeira.behavior.Pageable;
import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.enums.EnumItemSearch;
import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.excepitonhandler.BusinessException;
import com.coelho.fazfeira.excepitonhandler.NotFoundException;
import com.coelho.fazfeira.excepitonhandler.ShoppingListStatusException;
import com.coelho.fazfeira.flow.item.CreateProductAndItemFlowBuilder;
import com.coelho.fazfeira.handlers.DefaultContext;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.mapper.ItemMapper;
import com.coelho.fazfeira.model.*;
import com.coelho.fazfeira.repository.ItemRepository;
import com.coelho.fazfeira.repository.PriceHistoryRepository;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.repository.UnitRepository;
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
import java.util.*;

import static com.coelho.fazfeira.util.Converts.asString;
import static com.coelho.fazfeira.util.Nullables.isNotNull;

@org.springframework.stereotype.Service
public class ItemService implements Service<ItemDto, ItemDto>, Pageable {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private UnitRepository unitRepository;

    ItemMapper itemMapper = ItemMapper.INSTANCE;

    Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private InputValidator<ItemDto> inputValidator;

    @Autowired
    HttpServletRequest request;


    @Autowired
    private  UserService userService;

    @Autowired
    private CreateProductAndItemFlowBuilder createProductAndItemFlowBuilder;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ItemDto createWithProduct(ItemWithPorductInput obj) {
        DefaultContext context = DefaultContext.builder().build();
        context.setItemWithProductInput(obj);
        createProductAndItemFlowBuilder.create(context).build().run();
        return context.getItemDto();
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        Item item = validateAndConvert(itemDto);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        User user = User.builder().id(userService.getLoggedUserId()).build();

        final Optional<ShoppingList> shoppingListPage = this.shoppingListRepository
                .findByIdAndUser(itemDto.getShoppingList().getId(), user);

        if (shoppingListPage.isEmpty()) {
            logger.warn("Shopping list does not exist for this user");
            throw new BusinessException(BusinessCode.SHOPPING_LIST_NOT_EXIST_FOR_USER);
        }

        if (shoppingListPage.get().getStatus() == ShoppingListStatus.READY) {
            logger.warn("You cannot add items to lists with READY status.");
            throw new BusinessException(BusinessCode.SHOPPING_LIST_STATUS_NOT_ADD_ITEMS);
        }

        Unit unit = unitRepository.findById(item.getUnit().getId()).orElseThrow(
                ()-> new BusinessException(BusinessCode.UNIT_NOT_EXIST));

        ShoppingList shoppingList = shoppingListPage.get();
        Product product = item.getProduct();
        final Optional<Item> itemPage = this.itemRepository.findByShoppingListAndProduct(shoppingList, product);

        PriceHistory priceHistory = null;

        if (itemPage.isPresent()) {
            Item itemSaved = itemPage.get();

            itemMapper.updateItemFromItemDto(itemDto, itemSaved);

            itemSaved.setUpdatedAt(LocalDateTime.now());

            double newPrice = BigDecimal.valueOf(itemSaved.getPerUnit() * itemSaved.getQuantity())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            itemSaved.setPrice(newPrice);
            itemSaved.setAdded(true);
            item = itemSaved;

            Optional<PriceHistory> optionalPriceHistory = this.priceHistoryRepository
                    .findByProductAndItemAndShoppingList(product, itemSaved, shoppingList);

            if (optionalPriceHistory.isPresent()) {
                priceHistory = optionalPriceHistory.get();
                priceHistory.setUpdatedAt(LocalDateTime.now());
                priceHistory.setProduct(item.getProduct());
                priceHistory.setPrice(item.getPerUnit());
                priceHistory.setItem(item);
                priceHistory.setSupermarket(shoppingList.getSupermarket());
                priceHistory.setShoppingList(shoppingList);
            }

        } else {
            priceHistory = PriceHistory.builder()
                    .createdAt(LocalDateTime.now())
                    .product(item.getProduct())
                    .shoppingList(shoppingList)
                    .updatedAt(LocalDateTime.now())
                    .supermarket(shoppingList.getSupermarket())
                    .price(item.getPerUnit())
                    .item(item)
                    .build();
        }

        item.setUnit(unit);

        this.itemRepository.save(item);
        this.priceHistoryRepository.save(priceHistory);
        shoppingList.setUpdatedAt(LocalDateTime.now());
        this.shoppingListRepository.save(shoppingList);

        return this.itemMapper.itemToItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {

        User user = User.builder().id(userService.getLoggedUserId()).build();
        final Optional<ShoppingList> shoppingListPage = this.shoppingListRepository
                .findByIdAndUser(itemDto.getShoppingList().getId(), user);
        if (shoppingListPage.isEmpty()) {
            logger.warn("Shopping list does not exist for this user");
            throw new NotFoundException("Shopping list does not exist for this user");
        }

        final Item itemSaved = this.itemRepository.findById(itemDto.getId()).orElseThrow(()->{
            throw new NotFoundException("Item does not exist");
        });

        Unit unit = unitRepository.findById(UUID.fromString(itemDto.getUnit().getId())).orElseThrow(
                ()-> new BusinessException(BusinessCode.UNIT_NOT_EXIST));


        final Item item = this.itemMapper.updateItemFromItemDto(itemDto, itemSaved);

        item.setUnit(unit);

        if(unit.isIntegerType()){
            double roundedNumber = Math.round(item.getQuantity() < 0.5 ? item.getQuantity() + 0.5 : item.getQuantity());
            item.setQuantity(roundedNumber);
        }



        item.setUpdatedAt(LocalDateTime.now());
        double newPrice = BigDecimal.valueOf(item.getPerUnit() * item.getQuantity())
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
        item.setPrice(newPrice);

        this.itemRepository.save(item);
        ShoppingList shoppingList = shoppingListPage.get();
        shoppingList.setUpdatedAt(LocalDateTime.now());
        this.shoppingListRepository.save(shoppingList);

        Optional<PriceHistory> optionalPriceHistory = this.priceHistoryRepository.findByProductAndItemAndShoppingList(
                item.getProduct(),
                item,
                item.getShoppingList());

        if (optionalPriceHistory.isPresent()) {
            PriceHistory priceHistory = optionalPriceHistory.get();
            priceHistory.setUpdatedAt(LocalDateTime.now());
            priceHistory.setProduct(item.getProduct());
            priceHistory.setPrice(item.getPerUnit());
            priceHistory.setItem(item);
            priceHistory.setSupermarket(item.getShoppingList().getSupermarket());
            priceHistory.setShoppingList(item.getShoppingList());

            this.priceHistoryRepository.save(priceHistory);
        }


        return this.itemMapper.itemToItemDto(item);
    }

    @Override
    public void delete(UUID id) {

        final Optional<Item> itemOptional = this.itemRepository.findById(id);
        if (itemOptional.isEmpty()) {
            logger.warn("Item does not exist");
            throw new NotFoundException("Item does not exist");
        }
        Item item = itemOptional.get();

        User user = User.builder().id(userService.getLoggedUserId()).build();
        final Optional<ShoppingList> shoppingListPage = this.shoppingListRepository
                .findByIdAndUser(item.getShoppingList().getId(),
                        user);
        if (shoppingListPage.isEmpty()) {
            logger.warn("Shopping list does not exist for this user");
            throw new NotFoundException("Shopping list does not exist for this user");
        }

        Optional<PriceHistory> optionalPriceHistory = this.priceHistoryRepository.findByProductAndItemAndShoppingList(
                item.getProduct(),
                item,
                item.getShoppingList());

        if (optionalPriceHistory.isPresent()) {
            PriceHistory price = optionalPriceHistory.get();
            price.setItem(null);
            this.priceHistoryRepository.save(price);
        }
        this.itemRepository.delete(item);
        ShoppingList shoppingList = shoppingListPage.get();
        shoppingList.setUpdatedAt(LocalDateTime.now());
        this.shoppingListRepository.save(shoppingList);


    }

    @Override
    public ResponseList<ItemDto> getByParams(Map<String, String> params) {

        EnumItemSearch enumItemSearch = EnumItemSearch.find(isNotNull(params.get(Params.PARAM_PRODUCT_DESC)),
                isNotNull(params.get(Params.ITEM_IS_ADDED)));

        final SearchBehavior searchBehavior = enumItemSearch.getSearchBehavior();
        Page<Item> page = searchBehavior.searchPage(this.itemRepository, params);

//        if (!page.isEmpty()) {
//            final Map<String, String> defaultParams = Params.getDefaultParams();
//            defaultParams.put("pageSize", "5");
//            defaultParams.put("sortBy", "updatedAt");
//            for (Item _item : page.getContent()) {
//                final List<PriceHistory> pageHistory = this.priceHistoryRepository.findWithProductNoItem(
//                        _item.getProduct().getCode(),
//                        _item.getId(),
//                        5);
//
//                if(pageHistory.isEmpty()){
//                    _item.getProduct().setPriceHistories(Set.of());
//                }else{
//                    _item.getProduct().setPriceHistories(Set.of(pageHistory.toArray(new PriceHistory[0])));
//                }
//            }
//
//
//
//        }

        return this.itemMapper.pageItemToResponseList(page, params);
    }

    @Override
    public Optional<ItemDto> getById(UUID id) {
        return Optional.empty();
    }

    private Item validateAndConvert(ItemDto itemDto) {
        inputValidator.validate(itemDto);
        logger.debug("Preparing object conversion ItemDto to Item. {}", itemDto);
        Item item = this.itemMapper.itemDtoToItem(itemDto);
        logger.info("Object converted successfully");
        return item;
    }

}
