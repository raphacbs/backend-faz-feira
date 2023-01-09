package com.coelho.fazfeira.behavior.supermarket;

import com.coelho.fazfeira.behavior.SearchBehavior;
import com.coelho.fazfeira.mapper.SupermarketMapper;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.repository.SupermarketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.util.Map;

import static com.coelho.fazfeira.constants.Params.SUPERMARKET_NAME;

public class SupermarketSearchByName implements SearchBehavior<Supermarket, SupermarketRepository> {
    private final Logger logger = LoggerFactory.getLogger(SupermarketSearchByName.class);
    private final SupermarketMapper supermarketMapper = SupermarketMapper.INSTANCE;

    @Override
    public Page<Supermarket> searchPage(SupermarketRepository repository, Map<String, Object> params) {
        String name = String.valueOf(params.get(SUPERMARKET_NAME));
        return repository.findByNameIgnoreCaseContaining(getPageable(params), name);
    }
}
