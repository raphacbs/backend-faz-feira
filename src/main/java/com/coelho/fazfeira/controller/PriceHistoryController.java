package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.PriceHistoryDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.service.PriceHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/price-histories")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @PostMapping
    public ResponseEntity<PriceHistoryDto> register(@RequestBody PriceHistoryDto priceHistoryDto) {
        return new ResponseEntity<>(priceHistoryService.create(priceHistoryDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PriceHistoryDto> update(@RequestBody PriceHistoryDto priceHistoryDto) {
        return new ResponseEntity<>(priceHistoryService.update(priceHistoryDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseList<PriceHistoryDto>> get(
            @RequestHeader(value = Params.PARAM_PRODUCT_CODE) String productCode,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) String pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) String pageSize,
            @RequestParam(value = "sortBy", defaultValue = "description", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        Map<String, String> params = new HashMap<>();
        params.put(Params.PARAM_PRODUCT_CODE, productCode);
        params.put(Params.NO_PAGE, pageNo);
        params.put(Params.PAGE_SIZE, pageSize);
        params.put(Params.SORT_BY, sortBy);
        params.put(Params.SORT_DIR, sortDir);

        return ResponseEntity.ok(this.priceHistoryService.getByParams(params));
    }

    @GetMapping(path = "/{id:^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$}")
    public ResponseEntity<PriceHistoryDto> getById(@PathVariable("id") UUID id) {
        final Optional<PriceHistoryDto> optionalUnitDto = this.priceHistoryService.getById(id);
        return optionalUnitDto.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.noContent().build()
                );
    }

    @DeleteMapping(path = "/{id:^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$}")
    public ResponseEntity remove(@PathVariable("id") UUID id){
        this.priceHistoryService.delete(id);
        return ResponseEntity.ok().build();
    }


}
