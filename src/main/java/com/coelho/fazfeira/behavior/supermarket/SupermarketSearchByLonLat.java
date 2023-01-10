package com.coelho.fazfeira.behavior.supermarket;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.mapper.SupermarketMapper;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.repository.SupermarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Map;

import static com.coelho.fazfeira.constants.Params.*;

public class SupermarketSearchByLonLat implements SearchBehavior<Supermarket, SupermarketRepository> {

    private final Logger logger = LoggerFactory.getLogger(SupermarketSearchByLonLat.class);
    private final SupermarketMapper supermarketMapper = SupermarketMapper.INSTANCE;

    @Override
    public Page<Supermarket> searchPage(SupermarketRepository repository, Map<String, String> params) {
        double latitude = Double.parseDouble(String.valueOf(params.get(SUPERMARKET_LATITUDE)));
        double longitude = Double.parseDouble(String.valueOf(params.get(SUPERMARKET_LONGITUDE)));
        double radius =Double.parseDouble(String.valueOf(params.get(SUPERMARKET_RADIUS))) / 1000;

        final List<Supermarket> supermarkets = repository.findSupermarketWithInDistance(latitude, longitude, radius);
        return new PageImpl<Supermarket>(supermarkets, getPageable(params), Integer.parseInt( params.get(PAGE_SIZE).toString()));
    }
}
