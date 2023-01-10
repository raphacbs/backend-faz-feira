package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.model.Item;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    Item itemDtoToItem(ItemDto itemDto);

    ItemDto itemToItemDto(Item item);

    List<ItemDto> itemToItemDto(List<Item> item);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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
        return UUID.fromString(value);
    }

    default String map(UUID value) {
        return value.toString();
    }
}
