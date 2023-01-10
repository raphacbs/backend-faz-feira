package com.coelho.fazfeira.behavior.supermarket;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.dto.PlaceDto;
import com.coelho.fazfeira.mapper.SupermarketMapper;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.repository.SupermarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static com.coelho.fazfeira.constants.Params.*;

public class SupermarketSearchAPI implements SearchBehavior<Supermarket, SupermarketRepository> {
    private final Logger logger = LoggerFactory.getLogger(SupermarketSearchAPI.class);
    private final SupermarketMapper supermarketMapper = SupermarketMapper.INSTANCE;

    @Override
    public Page<Supermarket> searchPage(SupermarketRepository repository, Map<String, String> params) {
        String apiKey = System.getenv("X_API_PLACE_TOKEN");
        String latitude = params.get(SUPERMARKET_LATITUDE);
        String longitude = params.get(SUPERMARKET_LONGITUDE);
        double radius =Double.parseDouble(String.valueOf(params.get(SUPERMARKET_RADIUS)));

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String url = MessageFormat.format("https://api.geoapify.com/v2/places" +
                        "?categories=commercial.supermarket,commercial.marketplace" +
                        "&filter=circle:{0},{1},{2}&limit=20&apiKey={3}",
                longitude,
                latitude,
                radius,
                apiKey);
        try {
            logger.info("Place api url: {}", url);
            final ResponseEntity<PlaceDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity,
                    PlaceDto.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("returned status code 200");
                logger.info("getting response body´s");
                final PlaceDto placeDto = response.getBody();
                assert placeDto != null;
                final List<PlaceDto.Feature> features = placeDto.getFeatures();
                logger.info("Converting features to supermarket entity");
                final List<Supermarket> supermarkets = supermarketMapper.featureToSupermarket(placeDto.getFeatures());


                supermarkets.forEach(supermarket -> {
                    logger.info("Verifying if exist in database");
                    final Page<Supermarket> supermarketPage = repository.findByPlaceId(supermarket.getPlaceId(),
                            getPageable(params));

                    if (supermarketPage.isEmpty()) {
                        logger.info("Supermarket not exist in database");
                        logger.info("Preparing to save");
                        if(supermarket.getName() == null){
                            supermarket.setName("Desconhecido");
                        }
                        repository.save(supermarket);
                        logger.info("Supermarket save successfully");
                        logger.debug("Supermarket: {}", supermarket);
                    } else {
                        supermarket = (Supermarket) supermarketPage.getContent().get(0);
                    }

                });
                return new PageImpl<>(supermarkets);

            } else {
                logger.warn("Place API not return success");
                logger.warn("Place API return status code: {}", response.getStatusCode());

                return new PageImpl<>(List.of());
            }

        } catch (Exception ex) {
            logger.error("Error in search place api");
            logger.error(ex.getMessage());
            return new PageImpl<>(List.of());
        }

    }
}
