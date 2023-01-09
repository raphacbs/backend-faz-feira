package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.Pageable;
import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.enums.EnumShoppingListSearch;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.dto.ShoppingListRequest;
import com.coelho.fazfeira.excepitonhandler.EntityNotExistException;
import com.coelho.fazfeira.mapper.ShoppingListMapper;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import com.coelho.fazfeira.repository.ShoppingListRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.*;
import static com.coelho.fazfeira.util.Nullables.isNotNull;

@org.springframework.stereotype.Service
public class ShoppingListService implements Service<ShoppingListDto, ShoppingListRequest>, Pageable {

    private final Logger logger = LoggerFactory.getLogger(ShoppingListService.class);
    private final ShoppingListMapper shoppingListMapper = ShoppingListMapper.INSTANCE;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    private InputValidator<ShoppingListRequest> inputValidator;


    @Override
    public ShoppingListDto create(ShoppingListRequest obj) {
        final ShoppingList shoppingList = validateAndConvert(obj);
        shoppingList.setCreatedAt(LocalDateTime.now());
        shoppingList.setUpdateAt(LocalDateTime.now());
        shoppingList.setOpen(true);
        shoppingList.setUser(User.builder().id(getUserId()).build());
        this.shoppingListRepository.save(shoppingList);
        return this.shoppingListMapper.shoppingListToShoppingListDto(shoppingList);
    }

    @Override
    public ShoppingListDto update(ShoppingListRequest obj) {
        final ShoppingList shoppingList = validateAndConvert(obj);
        final Optional<ShoppingList> listOptional = this.shoppingListRepository.findById(shoppingList.getId());
        if(listOptional.isEmpty()){
            logger.warn("ShoppingList not found");
            throw new EntityNotExistException("ShoppingList not found");
        }
        ShoppingList shoppingListToSave =  listOptional.get();
        shoppingListToSave.setOpen(shoppingList.isOpen());
        shoppingListToSave.setUpdateAt(LocalDateTime.now());
        shoppingListToSave.setDescription(shoppingList.getDescription());
        shoppingListToSave.setSupermarket(shoppingList.getSupermarket());
        this.shoppingListRepository.save(shoppingListToSave);
        return this.shoppingListMapper.shoppingListToShoppingListDto(shoppingListToSave);
    }

    @Override
    public ShoppingListDto delete(UUID id) {
        return null;
    }

    @Override
    public ResponseList<ShoppingListDto> getByParams(Map<String, Object> params) {

        params.put(USER_ID, getUserId());

        EnumShoppingListSearch search = EnumShoppingListSearch.find(isNotNull(params.get(DESCRIPTION)),
                isNotNull(params.get(USER_ID)),
                isNotNull(params.get(SHOPPING_LIST_IS_OPEN)));

        final SearchBehavior searchBehavior = search.getSearchBehavior();
        Page<ShoppingListDto> page = searchBehavior.searchPage(this.shoppingListRepository, params);

        return shoppingListMapper.pageSupermarketToResponseList(searchBehavior.searchPage(this.shoppingListRepository, params),
                params);
    }

    private UUID getUserId() {
        Map<String, String> map = (Map<String, String>) request.getAttribute("claims");
        return UUID.fromString(map.get("sub"));
    }

    @Override
    public Optional<ShoppingListDto> getById(UUID id) {
        final Optional<ShoppingList> shoppingListOptional = this.shoppingListRepository.findById(id);
        if(shoppingListOptional.isEmpty()){
            return Optional.empty();
        }else{
            return Optional.of(this.shoppingListMapper.shoppingListToShoppingListDto(shoppingListOptional.get()));
        }
    }

    private ShoppingList validateAndConvert(ShoppingListRequest shoppingListRequest) {
        inputValidator.validate(shoppingListRequest);
        logger.debug("Preparing object conversion UnitRequestBody to Unit. {}", shoppingListRequest);
        ShoppingList shoppingList =  this.shoppingListMapper.shoppingListRequestToShoppingList(shoppingListRequest);
        logger.info("Object converted successfully");
        return shoppingList;
    }


}
