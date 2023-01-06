package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.behavior.enums.EnumProductBuilder;
import com.coelho.fazfeira.behavior.enums.EnumProductSearchBehavior;
import com.coelho.fazfeira.dto.ProductDto;
import com.coelho.fazfeira.dto.ProductRequest;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.excepitonhandler.EntityAlreadyExistException;
import com.coelho.fazfeira.excepitonhandler.EntityNotExistException;
import com.coelho.fazfeira.excepitonhandler.ResourceValidationException;
import com.coelho.fazfeira.mapper.ProductMapper;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.ProductRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.DESCRIPTION;
import static com.coelho.fazfeira.constants.Params.PRODUCT_CODE;
import static com.coelho.fazfeira.util.Nullables.isNotNull;
import static com.coelho.fazfeira.util.Nullables.isNull;

@Service
public class ProductService  {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository productRepository;
    private ProductMapper productMapper = ProductMapper.INSTANCE;
    @Autowired
    private InputValidator<ProductRequest> inputValidator;

    public ProductDto create(ProductRequest obj) {
        inputValidator.validate(obj);
        EnumProductBuilder enumProductBuilder = EnumProductBuilder.find(isNotNull(obj.getCode()));
        assert enumProductBuilder != null;
        final Product product = enumProductBuilder.getProductBuilderBehavior().build(obj);

        final Optional<Product> productOptional = this.productRepository.findByCode(product.getCode());

        if(productOptional.isEmpty()){
            this.productRepository.save(product);
            return this.productMapper.productToProductDto(product);
        }else{
            logger.warn("Already exist product with code {}",product.getCode() );
            throw new EntityAlreadyExistException("Already exist product with code  "+ productOptional.get().getCode());
        }

    }

    public ProductDto update(ProductRequest productRequest) {
        inputValidator.validate(productRequest);
        if(isNull(productRequest.getCode())){
            throw new ResourceValidationException("Property 'code' not found");
        }
        final Optional<Product> productOptional = this.productRepository.findByCode(productRequest.getCode());
        if(productOptional.isEmpty()){
            final String message = MessageFormat.format("Product with code {0} not found", productRequest.getCode());
            logger.warn(message);
            throw new EntityNotExistException(message);
        }
        Product product = productOptional.get();
        this.productMapper.updateProductFromProductRequest(productRequest, product);
        product.setDescription(product.getDescription().toUpperCase());
        product.setUpdateAt(LocalDateTime.now());
        this.productRepository.save(product);
        return this.productMapper.productToProductDto(product);
    }

    public ProductDto delete(UUID id) {
        return null;
    }

    public ResponseList<ProductDto> getByParams(Map<String, Object> params) {

        EnumProductSearchBehavior productSearchBehavior =
                EnumProductSearchBehavior.find(isNotNull(params.get(DESCRIPTION)),
                        false,
                        isNotNull(params.get(PRODUCT_CODE)));

        assert productSearchBehavior != null;
        SearchBehavior searchBehavior = productSearchBehavior.getUnitSearchBehavior();

        Page<Product> page = searchBehavior.searchPage(this.productRepository, params);

        if(page.isEmpty()){
            productSearchBehavior = EnumProductSearchBehavior
                    .find(isNotNull(params.get(DESCRIPTION)),
                            true,
                            isNotNull(params.get(PRODUCT_CODE)));

            searchBehavior = productSearchBehavior.getUnitSearchBehavior();

            page = searchBehavior.searchPage(this.productRepository, params);
        }

        return this.productMapper.pageUnitToResponseList(page);
    }

    public Optional<ProductDto> getById(String id) {
        final Optional<Product> optionalProduct = this.productRepository.findById(id);
        return optionalProduct.map(this.productMapper::productToProductDto);
    }
}
