package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ProductDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.SupermarketDto;
import com.coelho.fazfeira.service.SupermarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.SUPERMARKET_NAME;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/supermarkets")
public class SupermarketController {

    private final SupermarketService supermarketService;

    public SupermarketController(SupermarketService supermarketService) {
        this.supermarketService = supermarketService;
    }

    @GetMapping
    public ResponseEntity<ResponseList<SupermarketDto>> get(
            @RequestParam(value = SUPERMARKET_NAME, required = false) String name,
            @RequestParam(value = Params.SUPERMARKET_LONGITUDE, required = false) String longitude,
            @RequestParam(value = Params.SUPERMARKET_LATITUDE, required = false) String latitude,
            @RequestParam(value = Params.SUPERMARKET_RADIUS, required = false) String radius,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SUPERMARKET_NAME, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        Map<String, Object> params = new HashMap<>();
        params.put(SUPERMARKET_NAME, name);
        params.put(Params.SUPERMARKET_LONGITUDE, longitude);
        params.put(Params.SUPERMARKET_LATITUDE, latitude);
        params.put(Params.SUPERMARKET_RADIUS, radius);
        params.put(Params.NO_PAGE, pageNo);
        params.put(Params.PAGE_SIZE, pageSize);
        params.put(Params.SORT_BY, sortBy);
        params.put(Params.SORT_DIR, sortDir);

        return ResponseEntity.ok(this.supermarketService.getByParams(params));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<SupermarketDto> getById(@PathVariable("id") UUID id) {
        final Optional<SupermarketDto> optionalProductDto = this.supermarketService.getById(id);
        return optionalProductDto.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.noContent().build()
                );
    }

}
