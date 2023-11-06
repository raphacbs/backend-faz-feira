package com.coelho.fazfeira.service;

import com.coelho.fazfeira.behavior.Pageable;
import com.coelho.fazfeira.dto.PriceHistoryDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.excepitonhandler.EntityNotExistException;
import com.coelho.fazfeira.excepitonhandler.NotFoundException;
import com.coelho.fazfeira.mapper.PriceHistoryMapper;
import com.coelho.fazfeira.model.PriceHistory;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.repository.PriceHistoryRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.PARAM_PRODUCT_CODE;
import static com.coelho.fazfeira.constants.Params.getDefaultParams;

@org.springframework.stereotype.Service
public class PriceHistoryService implements Service<PriceHistoryDto, PriceHistoryDto>, Pageable {
    @Autowired
    private InputValidator<PriceHistoryDto> inputValidator;

    private final Logger logger = LoggerFactory.getLogger(PriceHistoryService.class);

    private final PriceHistoryMapper historyMapper = PriceHistoryMapper.INSTANCE;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Override
    public PriceHistoryDto create(PriceHistoryDto obj) {
        final PriceHistory priceHistory = validateAndConvert(obj);
        priceHistory.setCreatedAt(LocalDateTime.now());
        priceHistory.setUpdatedAt(LocalDateTime.now());
        this.priceHistoryRepository.save(priceHistory);
        return this.historyMapper.priceHistoryToPriceHistoryDto(priceHistory);
    }

    @Override
    public PriceHistoryDto update(PriceHistoryDto obj) {
        validateAndConvert(obj);
        final Optional<PriceHistory> historyOptional = this.priceHistoryRepository.findById(obj.getId());
        if (historyOptional.isEmpty()){
            logger.warn("price history not found");
            throw new NotFoundException("price history not found");
        }
        PriceHistory priceHistory = historyOptional.get();
        this.historyMapper.updatePriceHistoryFromPriceHistoryDto(obj, priceHistory);
        priceHistory.setUpdatedAt(LocalDateTime.now());
        this.priceHistoryRepository.save(priceHistory);
        return this.historyMapper.priceHistoryToPriceHistoryDto(priceHistory);
    }

    @Override
    public void delete(UUID id) {
        final Optional<PriceHistory> historyOptional = this.priceHistoryRepository.findById(id);
        if (historyOptional.isEmpty()){
            logger.warn("price history not found");
            throw new NotFoundException("price history not found");
        }
        this.priceHistoryRepository.delete(historyOptional.get());
    }

    @Override
    public ResponseList<PriceHistoryDto> getByParams(Map<String, String> params) {
        Product product = Product.builder().code(params.get(PARAM_PRODUCT_CODE)).build();
        final Page<PriceHistory> prices = this.priceHistoryRepository.findByProduct(getPageable(params), product);
        return this.historyMapper.pagePriceToResponseList(prices);
    }

    @Override
    public Optional<PriceHistoryDto> getById(UUID id) {
        return Optional.empty();
    }



    private PriceHistory validateAndConvert(PriceHistoryDto priceHistoryDto) {
        inputValidator.validate(priceHistoryDto);
        logger.debug("Preparing object conversion PriceHistoryDto to PriceHistory. {}", priceHistoryDto);
        PriceHistory priceHistory = this.historyMapper.priceHistoryDtoToPriceHistory(priceHistoryDto);
        logger.info("Object converted successfully");
        return priceHistory;
    }
}
