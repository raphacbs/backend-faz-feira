package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ItemDto;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.ShoppingListDto;
import com.coelho.fazfeira.excepitonhandler.MessageExceptionHandler;
import com.coelho.fazfeira.inputs.ShoppingListInput;
import com.coelho.fazfeira.service.ShoppingListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.coelho.fazfeira.constants.Params.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/shopping-lists")
@Tag(name = "ShoppingList operations")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


    @Operation(description = "Get all the ShoppingLists by user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns a list of the user's shopping lists",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = List.class, subTypes = {ShoppingListDto.class})
                    )
            ),
//            @ApiResponse(
//                    responseCode = "400",
//                    description = "Bad Request",
//                    content = {
//                            @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = MessageExceptionHandler.class),
//                                    examples = {
//                                            @ExampleObject(name = "300010",value = "{\"code\": 3000010, \"message\": \"Not found supermarket with Id\"}")
//                                    }
//                            ),
//                    }
//            )
    })
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

    @Operation(description = "Create a ShoppingList")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "ShoppingList created",

                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingListDto.class)

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageExceptionHandler.class),
                                    examples = {
                                            @ExampleObject(name = "200010", value = "{\"code\": 2000010, \"message\": \"Shopping list does not exist for this user\"}")
                                    }
                            ),
                    }
            )
    })
    @PostMapping
    public ResponseEntity<ShoppingListDto> create(@RequestBody ShoppingListInput shoppingListInput) {
        return new ResponseEntity<>(this.shoppingListService.create(shoppingListInput), HttpStatus.CREATED);
    }


    @Operation(description = "Change a ShoppingList")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ShoppingList changed",

                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingListDto.class)

                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageExceptionHandler.class),
                                    examples = {
                                            @ExampleObject(name = "300010", value = "{\"code\": 3000010, \"message\": \"Not found supermarket with Id\"}")
                                    }
                            ),
                    }
            )
    })
    @PutMapping
    public ResponseEntity<ShoppingListDto> update(@RequestBody ShoppingListInput shoppingListInput) {
        return new ResponseEntity<>(this.shoppingListService.update(shoppingListInput), HttpStatus.OK);
    }
}
