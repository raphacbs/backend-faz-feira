package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.service.ItemService;
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
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> register(@RequestBody ItemDto itemDto) {
        return new ResponseEntity<>(itemService.create(itemDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ItemDto> update(@RequestBody ItemDto unitRequestBody) {
        return new ResponseEntity<>(itemService.update(unitRequestBody), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseList<ItemDto>> get(
            @RequestHeader(value = Params.ITEM_SHOPPING_LIST_ID) String shoppingListId,
            @RequestParam(value = Params.ITEM_PRODUCT_DESC, required = false) String description,
            @RequestParam(value = Params.ITEM_IS_ADDED, required = false) String isAdded,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) String pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) String pageSize,
            @RequestParam(value = "sortBy", defaultValue = "description", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        Map<String, String> params = new HashMap<>();
        params.put(Params.ITEM_PRODUCT_DESC, description);
        params.put(Params.ITEM_IS_ADDED, isAdded);
        params.put(Params.ITEM_SHOPPING_LIST_ID, shoppingListId);
        params.put(Params.NO_PAGE, pageNo);
        params.put(Params.PAGE_SIZE, pageSize);
        params.put(Params.SORT_BY, sortBy);
        params.put(Params.SORT_DIR, sortDir);

        return ResponseEntity.ok(this.itemService.getByParams(params));
    }

    @GetMapping(path = "/{id:^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$}")
    public ResponseEntity<ItemDto> getById(@PathVariable("id") UUID id) {
        final Optional<ItemDto> optionalUnitDto = this.itemService.getById(id);
        return optionalUnitDto.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.noContent().build()
                );
    }

    @DeleteMapping(path = "/{id:^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-4[0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$}")
    public ResponseEntity remove(@PathVariable("id") UUID id){
        this.itemService.delete(id);
        return ResponseEntity.ok().build();
    }


}
