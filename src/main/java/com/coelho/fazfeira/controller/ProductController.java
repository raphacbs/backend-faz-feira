package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.*;
import com.coelho.fazfeira.service.ProductService;
import com.coelho.fazfeira.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> register(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.create(productRequest), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(productService.update(productRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseList<ProductDto>> get(
            @RequestParam(value = Params.DESCRIPTION, required = false) String description,
            @RequestParam(value = Params.PRODUCT_CODE, required = false) String code,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "description", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        Map<String, Object> params = new HashMap<>();
        params.put(Params.DESCRIPTION, description);
        params.put(Params.PRODUCT_CODE, code);
        params.put(Params.NO_PAGE, pageNo);
        params.put(Params.PAGE_SIZE, pageSize);
        params.put(Params.SORT_BY, sortBy);
        params.put(Params.SORT_DIR, sortDir);

        return ResponseEntity.ok(this.productService.getByParams(params));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable("id") String id) {
        final Optional<ProductDto> optionalProductDto = this.productService.getById(id);
        return optionalProductDto.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.noContent().build()
                );
    }

}
