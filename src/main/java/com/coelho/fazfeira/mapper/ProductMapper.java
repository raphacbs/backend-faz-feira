package com.coelho.fazfeira.mapper;

import com.coelho.fazfeira.dto.ProductCosmoDto;
import com.coelho.fazfeira.dto.ProductDto;
import com.coelho.fazfeira.dto.ProductRequest;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.model.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productDtoToProduct(ProductDto productDto);
    List<Product> productDtoToProduct(List<ProductDto> productDto);

    ProductDto productToProductDto(Product product);
    Product productRequestToProduct(ProductRequest productRequest);

    List<ProductDto> productToProductDto(List<Product> products);

    @Mapping(source = "gtin", target = "code")
    @Mapping(source = "brand.name", target = "brand")
    ProductDto productCosmoToProductDto(ProductCosmoDto productCosmoDto);

    List<ProductDto> productCosmoToProductDto(List<ProductCosmoDto> productCosmoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductDto(ProductDto productDto, @MappingTarget Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product updateProductFromProductRequest(ProductRequest productRequest, @MappingTarget Product product);


    @ObjectFactory
    default ResponseList<ProductDto> pageProductToResponseList(Page<Product> productPage){
        ResponseList<ProductDto> unitResponseList = new ResponseList<>();
        unitResponseList.setItems(productToProductDto(productPage.get().toList()));
        unitResponseList.setPageNo(productPage.getNumber()+1);
        unitResponseList.setLast(productPage.isLast());
        unitResponseList.setPageSize(productPage.getSize());
        unitResponseList.setTotalPages(productPage.getTotalPages());
        unitResponseList.setTotalElements(productPage.getTotalElements());
        return unitResponseList;
    }

    default UUID map(String value){
        if(value == null){
            return null;
        }
        return UUID.fromString(value);
    }
    default String map(UUID value){
        if(value == null){
            return null;
        }
        return value.toString();
    }
}
