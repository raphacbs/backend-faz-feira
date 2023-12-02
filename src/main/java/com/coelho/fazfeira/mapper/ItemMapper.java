package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.inputs.ItemInput;
import com.coelho.fazfeira.inputs.ItemWithPorductInput;
import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.Unit;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring",imports = {UUID.class})
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @NullToUnitDto
    default Unit nullUnitDto(UnitDto unit) {
        return unit == null ? defaultUnit() : Unit.builder()
                .id(UUID.fromString(unit.getId()))
                .build();
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface NullToUnitDto {

    }

    private Unit defaultUnit() {
        return Unit.builder()
                .id(UUID.fromString("3e7ec246-3e46-4c3b-b81e-1294b6643777"))
                .build();
    }


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "unit" , target = "unit", qualifiedBy = NullToUnitDto.class)
    Item itemDtoToItem(ItemDto itemDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item itemInputToItem(ItemInput itemInput);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Item itemWithProductInputToItem(ItemWithPorductInput itemInput);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "product.priceHistories", ignore = true)
    @Mapping(source = "unit.integerType" , target = "unit.isIntegerType")
    ItemDto itemToItemDto(Item item);

    List<ItemDto> itemToItemDto(List<Item> item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "shoppingList", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "unit", expression = "java(Unit.builder().id(UUID.fromString(itemDto.getUnit().getId())).build())")
    Item updateItemFromItemDto(ItemDto itemDto, @MappingTarget Item item);

    @ObjectFactory
    default ResponseList<ItemDto> pageItemToResponseList(Page<Item> itemPage,
                                                         Map<String, String> params) {

        ResponseList<ItemDto> unitResponseList = new ResponseList<>();
        unitResponseList.setItems(itemToItemDto(itemPage.getContent()));
        unitResponseList.setPageNo(itemPage.getNumber() + 1);
        unitResponseList.setLast(itemPage.isLast());
        unitResponseList.setPageSize(itemPage.getSize());
        unitResponseList.setTotalPages(itemPage.getTotalPages());
        unitResponseList.setTotalElements(itemPage.getTotalElements());
        return unitResponseList;
    }

    default UUID map(String value) {
        if(value == null){
            return null;
        }
        return UUID.fromString(value);
    }

    default String map(UUID value) {
        if(value == null){
            return null;
        }
        return value.toString();
    }
}
