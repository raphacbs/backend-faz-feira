package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.enums.EnumSupermarketSearch;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.SupermarketDto;
import com.coelho.fazfeira.mapper.SupermarketMapper;
import com.coelho.fazfeira.model.Supermarket;
import com.coelho.fazfeira.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.*;
import static com.coelho.fazfeira.util.Nullables.isNotNull;

@Service
public class SupermarketService {
    @Autowired
    private SupermarketRepository repository;

    SupermarketMapper supermarketMapper = SupermarketMapper.INSTANCE;

    public ResponseList<SupermarketDto> getByParams(Map<String, Object> params) {

        EnumSupermarketSearch search = EnumSupermarketSearch.find(isNotNull(params.get(SUPERMARKET_LONGITUDE)),
                isNotNull(params.get(SUPERMARKET_LATITUDE)),
                isNotNull(params.get(SUPERMARKET_NAME)),
                false);

        assert search != null;
        Page<Supermarket> supermarketPage = search.getSearchBehavior().searchPage(repository, params);

        if(supermarketPage.isEmpty()){
            search = EnumSupermarketSearch.find(isNotNull(params.get(SUPERMARKET_LONGITUDE)),
                    isNotNull(params.get(SUPERMARKET_LATITUDE)),
                    isNotNull(params.get(SUPERMARKET_NAME)),
                    true);

            supermarketPage = search.getSearchBehavior().searchPage(repository, params);
        }

        return supermarketMapper.pageSupermarketToResponseList(supermarketPage, params);
    }

    public Optional<SupermarketDto> getById(UUID id) {
        return Optional.empty();
    }
}
