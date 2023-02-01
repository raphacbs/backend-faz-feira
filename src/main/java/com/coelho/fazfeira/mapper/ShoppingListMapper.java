package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.dto.ShoppingListRequest;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.ShoppingList;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ShoppingListMapper {
    ShoppingListMapper INSTANCE = Mappers.getMapper(ShoppingListMapper.class);

    @ObjectFactory
    default ResponseList<ShoppingListDto> pageSupermarketToResponseList(Page<ShoppingList> shoppingListPage,
                                                                        Map<String, String> params) {
        final List<ShoppingListDto> shoppingListDtos = new ArrayList<>();
        final List<ShoppingList> shoppingLists = shoppingListPage.get().toList();
        shoppingLists.forEach(shoppingList -> {
            final ShoppingListDto shoppingListDto = shoppingListToShoppingListDto(shoppingList);

            Set<Item> items = shoppingList.getItems();

            double plannedTotalValue = BigDecimal.valueOf(items.stream().mapToDouble(Item::getPrice).sum())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            double totalValueAdded = BigDecimal.valueOf(items.stream().mapToDouble(item ->
                            item.isAdded() ? item.getPrice() : 0).sum())
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();

            int quantityPlannedProduct = items.stream().mapToInt(Item::getQuantity).sum();

            int quantityAddedProduct = items.stream().mapToInt(item -> item.isAdded() ? item.getQuantity() : 0).sum();

            final ShoppingListDto.ItemsInfo itemsInfo = ShoppingListDto.ItemsInfo.builder()
                    .quantityPlannedProduct(quantityPlannedProduct)
                    .quantityAddedProduct(quantityAddedProduct)
                    .plannedTotalValue(plannedTotalValue)
                    .totalValueAdded(totalValueAdded)
                    .build();
            shoppingListDto.setItemsInfo(itemsInfo);
            shoppingListDtos.add(shoppingListDto);
        });

        ResponseList<ShoppingListDto> unitResponseList = new ResponseList<>();
        unitResponseList.setItems(shoppingListDtos);
        unitResponseList.setPageNo(shoppingListPage.getNumber() + 1);
        unitResponseList.setLast(shoppingListPage.isLast());
        unitResponseList.setPageSize(shoppingListPage.getSize());
        unitResponseList.setTotalPages(shoppingListPage.getTotalPages());
        unitResponseList.setTotalElements(shoppingListPage.getTotalElements());
        return unitResponseList;
    }

    @Mapping(source = "supermarketId", target = "supermarket.id")
    ShoppingList shoppingListDtoToShoppingList(ShoppingListDto shoppingListDto);

    @Mapping(source = "supermarketId", target = "supermarket.id")
    ShoppingList shoppingListRequestToShoppingList(ShoppingListRequest shoppingListRequest);

    List<ShoppingList> shoppingListDtoToShoppingList(List<ShoppingListDto> shoppingListDto);

    @Mapping(source = "supermarket.id", target = "supermarketId")
    @Mapping(source = "supermarket.name", target = "supermarketName")
    ShoppingListDto shoppingListToShoppingListDto(ShoppingList shoppingList);

    List<ShoppingListDto> shoppingListToShoppingListDto(List<ShoppingList> shoppingList);

    @Mapping(source = "supermarketId", target = "supermarket.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShoppingList updateShoppingListFromShoppingListDto(ShoppingListDto shoppingListDto, @MappingTarget ShoppingList shoppingList);

    default UUID map(String value){
        if(value == null){
            return null;
        }
        return UUID.fromString(value);
    }

}
