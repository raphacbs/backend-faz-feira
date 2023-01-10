package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.dto.ShoppingListRequest;
import com.coelho.fazfeira.dto.SupermarketDto;
import com.coelho.fazfeira.service.ShoppingListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.coelho.fazfeira.constants.Params.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/shopping-lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping
    public ResponseEntity<ResponseList<ShoppingListDto>> get(
            @RequestParam(value = SHOPPING_LIST_SUPERMARKET_ID, required = false) String supermarketId,
            @RequestParam(value = DESCRIPTION, required = false) String description,
            @RequestParam(value = SHOPPING_LIST_STATUS, required = false) String status,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) String pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) String pageSize,
            @RequestParam(value = "sortBy", defaultValue = DESCRIPTION, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        Map<String, String> params = new HashMap<>();
        params.put(SHOPPING_LIST_SUPERMARKET_ID, supermarketId);
        params.put(SHOPPING_LIST_STATUS, status);
        params.put(DESCRIPTION, description);
        params.put(Params.NO_PAGE, pageNo);
        params.put(Params.PAGE_SIZE, pageSize);
        params.put(Params.SORT_BY, sortBy);
        params.put(Params.SORT_DIR, sortDir);

        return ResponseEntity.ok(this.shoppingListService.getByParams(params));
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ShoppingListDto> getById(@PathVariable("id") UUID id) {
        final Optional<ShoppingListDto> optionalProductDto = this.shoppingListService.getById(id);
        return optionalProductDto.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.noContent().build()
                );
    }

    @PostMapping
    public ResponseEntity<ShoppingListDto> create(@RequestBody ShoppingListRequest shoppingListRequest){
        return new ResponseEntity<>(this.shoppingListService.create(shoppingListRequest), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<ShoppingListDto> update(@RequestBody ShoppingListRequest shoppingListRequest){
        return new ResponseEntity<>(this.shoppingListService.update(shoppingListRequest), HttpStatus.OK);
    }
}
