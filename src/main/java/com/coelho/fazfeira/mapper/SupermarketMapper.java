package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.PlaceDto;
import com.coelho.fazfeira.dto.Property;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.SupermarketDto;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.util.Util;
import org.apache.lucene.util.SloppyMath;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.coelho.fazfeira.constants.Params.SUPERMARKET_LATITUDE;
import static com.coelho.fazfeira.constants.Params.SUPERMARKET_LONGITUDE;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SupermarketMapper {
    SupermarketMapper INSTANCE = Mappers.getMapper(SupermarketMapper.class);

    @Mapping(source = "state_code", target = "stateCode")
    @Mapping(source = "lat", target = "latitude")
    @Mapping(source = "lon", target = "longitude")
    @Mapping(source = "formatted", target = "address")
    @Mapping(source = "place_id", target = "placeId")
    Supermarket propertyToSupermarket(Property property);

    default List<Supermarket> featureToSupermarket(List<PlaceDto.Feature> features) {
        List<Supermarket> supermarkets = new ArrayList<>();
        features.forEach(feature -> {
            supermarkets.add(propertyToSupermarket(feature.getProperties()));
        });
        return supermarkets;
    }

    @ObjectFactory
    default ResponseList<SupermarketDto> pageSupermarketToResponseList(Page<Supermarket> supermarketPage,
                                                                       Map<String, Object> params) {
        double latitude = Double.parseDouble(String.valueOf(params.get(SUPERMARKET_LATITUDE)));
        double longitude = Double.parseDouble(String.valueOf(params.get(SUPERMARKET_LONGITUDE)));
        final List<SupermarketDto> supermarketDtos = supermarketToSupermarketDto(supermarketPage.get().toList());
        supermarketDtos.forEach(supermarketDto -> {
            BigDecimal bd = BigDecimal.valueOf(SloppyMath.haversinMeters(latitude,
                            longitude,
                            supermarketDto.getLatitude(),
                            supermarketDto.getLongitude()))
                    .setScale(2, RoundingMode.HALF_UP);
            supermarketDto.setDistance(bd.doubleValue());

        });
        ResponseList<SupermarketDto> unitResponseList = new ResponseList<>();
        unitResponseList.setItems(supermarketDtos);
        unitResponseList.setPageNo(supermarketPage.getNumber() + 1);
        unitResponseList.setLast(supermarketPage.isLast());
        unitResponseList.setPageSize(supermarketPage.getSize());
        unitResponseList.setTotalPages(supermarketPage.getTotalPages());
        unitResponseList.setTotalElements(supermarketPage.getTotalElements());
        return unitResponseList;
    }

    Supermarket supermarketDtoToSupermarket(SupermarketDto supermarketDto);

    SupermarketDto supermarketToSupermarketDto(Supermarket supermarket);

    List<SupermarketDto> supermarketToSupermarketDto(List<Supermarket> supermarket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Supermarket updateSupermarketFromSupermarketDto(SupermarketDto supermarketDto, @MappingTarget Supermarket supermarket);
}
