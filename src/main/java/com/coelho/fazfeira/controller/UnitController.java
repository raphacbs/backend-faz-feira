package com.coelho.fazfeira.controller;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("api/v1/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<UnitDto> register(@RequestBody UnitRequestBody unitRequestBody) {
        return new ResponseEntity<>(unitService.create(unitRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UnitDto> update(@RequestBody UnitRequestBody unitRequestBody) {
        return new ResponseEntity<>(unitService.update(unitRequestBody), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseList<UnitDto>> get(
            @RequestParam(value = Params.UNIT_DESCRIPTION, required = false) String description,
            @RequestParam(value = Params.UNIT_INITIALS, required = false) String initials,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "description", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        Map<String,Object> params = new HashMap<>();
        params.put(Params.UNIT_DESCRIPTION,description);
        params.put(Params.UNIT_INITIALS,initials);
        params.put(Params.NO_PAGE,pageNo);
        params.put(Params.PAGE_SIZE,pageSize);
        params.put(Params.SORT_BY,sortBy);
        params.put(Params.SORT_DIR,sortDir);

        return ResponseEntity.ok(this.unitService.getByParams(params));
    }
}
