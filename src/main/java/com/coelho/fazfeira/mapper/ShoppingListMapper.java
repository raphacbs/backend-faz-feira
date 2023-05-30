package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.Supermarket;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", imports = {UUID.class, Supermarket.class})
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

            double quantityPlannedProduct = items.stream().mapToDouble(Item::getQuantity).sum();

            double quantityAddedProduct = items.stream().mapToDouble(item -> item.isAdded() ? item.getQuantity() : 0).sum();

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
   // @Mapping(target = "supermarket.id", expression = "java(UUID.fromString(shoppingListDto.getSupermarketId()))")
    ShoppingList shoppingListDtoToShoppingList(ShoppingListDto shoppingListDto);

//    @Mapping(source = "supermarketId", target = "supermarket.id")
    @Mapping(target = "supermarket.id", expression = "java(UUID.fromString(shoppingListInput.getSupermarketId()))")

    ShoppingList shoppingListRequestToShoppingList(ShoppingListInput shoppingListInput);

    List<ShoppingList> shoppingListDtoToShoppingList(List<ShoppingListDto> shoppingListDto);

    @Mapping(source = "supermarket.id", target = "supermarketId")
    @Mapping(source = "supermarket.name", target = "supermarketName")
    ShoppingListDto shoppingListToShoppingListDto(ShoppingList shoppingList);

    List<ShoppingListDto> shoppingListToShoppingListDto(List<ShoppingList> shoppingList);

    @Mapping(source = "supermarketId", target = "supermarket.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShoppingList updateShoppingListFromShoppingListDto(ShoppingListDto shoppingListDto, @MappingTarget ShoppingList shoppingList);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   // @Mapping(source = "supermarketId", target = "supermarket.id")
    @Mapping(target = "supermarket", expression = "java(Supermarket.builder().id(UUID.fromString(shoppingListInput.getSupermarketId())).build())")
    @Mapping(target = "id", ignore = true)
    void updateShoppingListFromShoppingListInput(ShoppingListInput shoppingListInput,
                                                    @MappingTarget ShoppingList shoppingList);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    // @Mapping(source = "supermarketId", target = "supermarket.id")
    @Mapping(target = "supermarket", expression = "java(supermarket)")
    @Mapping(target = "id", ignore = true)
    void updateShoppingListFromShoppingListInput(ShoppingListInput shoppingListInput,
                                                 @MappingTarget ShoppingList shoppingList,
                                                 Supermarket supermarket);
    default UUID map(String value){

        if(value == null){
            return null;
        }
        return UUID.fromString(value);
    }

}
