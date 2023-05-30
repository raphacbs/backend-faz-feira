package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.ResponseList;

import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.model.Unit;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UnitMapper {

    UnitMapper INSTANCE = Mappers.getMapper(UnitMapper.class);


    @Mapping(source = "integerType" , target = "isIntegerType")
    Unit unitDtoToUnit(UnitDto unitDto);

    @Mapping(source = "integerType" , target = "isIntegerType")
    UnitDto unitToUnitDto(Unit unit);

    List<UnitDto> unitToUnitDto(List<Unit> unit);

    Unit unitRequestBodyToUnit(UnitRequestBody unitRequestBody);

    @ObjectFactory
    default ResponseList<UnitDto> pageUnitToResponseList(Page<Unit> unitPage){
        ResponseList<UnitDto> unitResponseList = new ResponseList<>();
        unitResponseList.setItems(unitToUnitDto(unitPage.get().toList()));
        unitResponseList.setPageNo(unitPage.getNumber()+1);
        unitResponseList.setLast(unitPage.isLast());
        unitResponseList.setPageSize(unitPage.getSize());
        unitResponseList.setTotalPages(unitPage.getTotalPages());
        unitResponseList.setTotalElements(unitPage.getTotalElements());
        return unitResponseList;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Unit updateUnitFromUnitDto(UnitDto unitDto, @MappingTarget Unit unit);

    default UUID map(String value) {
        return value != null ? UUID.fromString(value) : null;
    }


    default String map(UUID value) {
        return value != null ? value.toString() : null;
    }

    @ObjectFactory
    default ResponseList<Unit> createDto(){
        ResponseList<Unit> unitResponseList = new ResponseList<>();
        unitResponseList.setItems(List.of());
        return unitResponseList;
    }

}
