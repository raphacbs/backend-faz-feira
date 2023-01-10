package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.Pageable;
import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.enums.EnumItemSearch;
import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.excepitonhandler.NotFoundException;
import com.coelho.fazfeira.mapper.ItemMapper;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ItemRepository;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.util.Nullables.isNotNull;

@org.springframework.stereotype.Service
public class ItemService implements Service<ItemDto, ItemDto>, Pageable {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    ItemMapper itemMapper = ItemMapper.INSTANCE;

    Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private InputValidator<ItemDto> inputValidator;

    @Autowired
    HttpServletRequest request;

    @Override
    public ItemDto create(ItemDto itemDto) {
        Item item = validateAndConvert(itemDto);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        User user = User.builder().id(getUserId()).build();
        final Page<ShoppingList> shoppingListPage = this.shoppingListRepository
                .findByIdAndUser(getPageable(Params.getDefaultParams()),
                        itemDto.getShoppingList().getId(),
                        user);
        if(shoppingListPage.isEmpty()){
            logger.warn("Shopping list does not exist for this user");
            throw new NotFoundException("Shopping list does not exist for this user");
        }

        ShoppingList shoppingList = ShoppingList.builder().id(itemDto.getShoppingList().getId()).build();
        Product product = item.getProduct();
        final Optional<Item> itemPage = this.itemRepository.findByShoppingListAndProduct(
                shoppingList,
                product);

        if(itemPage.isPresent()){
            Item itemSaved = itemPage.get();
            final Integer quantity = itemSaved.getQuantity();
            itemSaved.setQuantity(quantity + itemDto.getQuantity());
            itemSaved.setPerUnit(itemDto.getPerUnit());

            double newPrice = BigDecimal.valueOf(itemSaved.getPerUnit() * itemSaved.getQuantity())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            itemSaved.setPrice(newPrice);
            item = itemSaved;
        }

        this.itemRepository.save(item);
        return this.itemMapper.itemToItemDto(item);
    }

    @Override
    public ItemDto update(ItemDto itemDto) {

        User user = User.builder().id(getUserId()).build();
        final Page<ShoppingList> shoppingListPage = this.shoppingListRepository
                .findByIdAndUser(getPageable(Params.getDefaultParams()),
                        itemDto.getShoppingList().getId(),
                        user);
        if(shoppingListPage.isEmpty()){
            logger.warn("Shopping list does not exist for this user");
            throw new NotFoundException("Shopping list does not exist for this user");
        }

        final Optional<Item> itemOptional = this.itemRepository.findById(itemDto.getId());
        if(itemOptional.isEmpty()){
            logger.warn("Item does not exist");
            throw new NotFoundException("Item does not exist");
        }

        final Item item = this.itemMapper.updateItemFromItemDto(itemDto, itemOptional.get());

        item.setUpdatedAt(LocalDateTime.now());

        this.itemRepository.save(item);

        return this.itemMapper.itemToItemDto(item);
    }

    @Override
    public void delete(UUID id) {

        final Optional<Item> itemOptional = this.itemRepository.findById(id);
        if(itemOptional.isEmpty()){
            logger.warn("Item does not exist");
            throw new NotFoundException("Item does not exist");
        }
        Item item = itemOptional.get();

        User user = User.builder().id(getUserId()).build();
        final Page<ShoppingList> shoppingListPage = this.shoppingListRepository
                .findByIdAndUser(getPageable(Params.getDefaultParams()),
                        item.getShoppingList().getId(),
                        user);
        if(shoppingListPage.isEmpty()){
            logger.warn("Shopping list does not exist for this user");
            throw new NotFoundException("Shopping list does not exist for this user");
        }

        this.itemRepository.delete(item);


    }

    @Override
    public ResponseList<ItemDto> getByParams(Map<String, String> params) {

        EnumItemSearch enumItemSearch = EnumItemSearch.find(isNotNull(params.get(Params.ITEM_PRODUCT_DESC)),
                isNotNull(params.get(Params.ITEM_IS_ADDED)));

        final SearchBehavior searchBehavior = enumItemSearch.getSearchBehavior();
        Page<Item> page =  searchBehavior.searchPage(this.itemRepository, params);

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

    private UUID getUserId() {
        Map<String, String> map = (Map<String, String>) request.getAttribute("claims");
        return UUID.fromString(map.get("sub"));
    }
}
