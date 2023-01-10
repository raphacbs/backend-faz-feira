package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.PriceHistoryDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.model.PriceHistory;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PriceHistoryMapper {
    PriceHistoryMapper INSTANCE = Mappers.getMapper(PriceHistoryMapper.class);
    PriceHistory priceHistoryDtoToPriceHistory(PriceHistoryDto priceHistoryDto);

    PriceHistoryDto priceHistoryToPriceHistoryDto(PriceHistory priceHistory);
    List<PriceHistoryDto> priceHistoryToPriceHistoryDto(List<PriceHistory> priceHistory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PriceHistory updatePriceHistoryFromPriceHistoryDto(PriceHistoryDto priceHistoryDto, @MappingTarget PriceHistory priceHistory);

    default ResponseList<PriceHistoryDto> pagePriceToResponseList(Page<PriceHistory> historyPage) {
        ResponseList<PriceHistoryDto> historyDtoResponseList = new ResponseList<>();
        historyDtoResponseList.setItems(priceHistoryToPriceHistoryDto(historyPage.get().toList()));
        historyDtoResponseList.setPageNo(historyPage.getNumber() + 1);
        historyDtoResponseList.setLast(historyPage.isLast());
        historyDtoResponseList.setPageSize(historyPage.getSize());
        historyDtoResponseList.setTotalPages(historyPage.getTotalPages());
        historyDtoResponseList.setTotalElements(historyPage.getTotalElements());
        return historyDtoResponseList;
    }
    default UUID map(String value) {
        if (value == null) {
            return null;
        }
        return UUID.fromString(value);
    }

    default String map(UUID value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}
