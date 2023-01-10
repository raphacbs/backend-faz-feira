package com.coelho.fazfeira.behavior.product;

import com.coelho.fazfeira.behavior.SearchBehavior;
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

public class ProductSearchCosmoDesc implements SearchBehavior<Product, ProductRepository> {
    private final Logger logger = LoggerFactory.getLogger(ProductSearchCosmoDesc.class);
    @Value("${com.coelho.faz.feira.cosmo.api.key}")
    private String cosmoApiKey;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;

    @Override
    public Page<Product> searchPage(ProductRepository repository, Map<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Cosmos-Token", System.getenv("X_COSMOS_TOKEN"));
        HttpEntity<Void> requestCosmo = new HttpEntity<>(headers);
        String description = params.get(DESCRIPTION);
        try {
            final ResponseEntity<ProductCosmoList> productCosmoDtoResponseEntity = restTemplate.exchange(
                    "https://api.cosmos.bluesoft.com.br/products/?query=" + description,
                    HttpMethod.GET,
                    requestCosmo,
                    ProductCosmoList.class);
            if (productCosmoDtoResponseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                logger.info("Product with ean '{}' not found in Cosmo API", description);
            }
            logger.info("Product with ean '{}' found in Cosmo API", description);
            final ProductCosmoList productCosmoList = productCosmoDtoResponseEntity.getBody();

            final List<ProductDto> productDtos = this.productMapper.productCosmoToProductDto(productCosmoList.getProducts());
            final List<Product> products = this.productMapper.productDtoToProduct(productDtos);

            products.forEach(product -> {
                product.setCreatedAt(LocalDateTime.now());
                product.setUpdateAt(LocalDateTime.now());
                repository.save(product);
            });

            return new PageImpl<>(products);

        } catch (Exception ex) {
            logger.error("Error in search cosmo api");
            logger.error(ex.getMessage());
            return new PageImpl<>(List.of());
        }

    }
}
