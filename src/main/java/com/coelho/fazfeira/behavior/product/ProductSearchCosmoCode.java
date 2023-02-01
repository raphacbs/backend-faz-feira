package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.dto.ProductCosmoDto;
import com.coelho.fazfeira.dto.ProductCosmoList;
import com.coelho.fazfeira.dto.ProductDto;
import com.coelho.fazfeira.mapper.ProductMapper;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.coelho.fazfeira.constants.Params.DESCRIPTION;
import static com.coelho.fazfeira.constants.Params.PRODUCT_CODE;

public class ProductSearchCosmoCode implements SearchBehavior<Product, ProductRepository> {
    private final Logger logger = LoggerFactory.getLogger(ProductSearchCosmoCode.class);
    @Value("${com.coelho.faz.feira.cosmo.api.key}")
    private String cosmoApiKey;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Page<Product> searchPage(ProductRepository repository, Map<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Cosmos-Token", System.getenv("X_COSMOS_TOKEN"));
        HttpEntity<Void> requestCosmo = new HttpEntity<>(headers);
        String code = params.get(PRODUCT_CODE);
        try {
            final ResponseEntity<ProductCosmoDto> productCosmoDtoResponseEntity = restTemplate.exchange(
                    "https://api.cosmos.bluesoft.com.br/gtins/" + code,
                    HttpMethod.GET,
                    requestCosmo,
                    ProductCosmoDto.class);
            if (productCosmoDtoResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("Product with ean '{}' not found in Cosmo API", code);
            }
            logger.info("Product with ean '{}' found in Cosmo API", code);
            final ProductCosmoDto productCosmoDto = productCosmoDtoResponseEntity.getBody();

            final ProductDto productDto = this.productMapper.productCosmoToProductDto(productCosmoDto);
            final Product product = this.productMapper.productDtoToProduct(productDto);
            product.setCreatedAt(LocalDateTime.now());
            product.setUpdateAt(LocalDateTime.now());
            repository.save(product);

            return new PageImpl<>(List.of(product));

        } catch (Exception ex) {
            logger.error("Error in search cosmo api");
            logger.error(ex.getMessage());
            return new PageImpl<>(List.of());
        }

    }
}
