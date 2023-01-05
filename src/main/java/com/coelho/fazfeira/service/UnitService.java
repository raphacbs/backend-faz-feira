package com.coelho.fazfeira.service;

import com.coelho.fazfeira.constants.Params;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.excepitonhandler.UnitAlreadyExistException;
import com.coelho.fazfeira.excepitonhandler.UnitNotExistException;
import com.coelho.fazfeira.mapper.UnitMapper;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.*;

import static com.coelho.fazfeira.util.Nullables.*;

@Service
public class UnitService implements IService<UnitDto, UnitRequestBody> {

    private static final Logger logger = LoggerFactory.getLogger(UnitService.class);

    @Autowired
    private UnitRepository unitRepository;
    private final UnitMapper unitMapper = UnitMapper.INSTANCE;

    @Autowired
    private InputValidator<UnitRequestBody> inputValidator;

    @Autowired
    HttpServletRequest request;

    @Override
    public UnitDto create(UnitRequestBody unitRequestBody) {
        logger.info("The create method was called");
        final Unit unitToSave = validateAndConvert(unitRequestBody);
        logger.info("Checking if Unit already exists");

        final EnumUnitSearchBehavior enumUnitSearchBehavior = EnumUnitSearchBehavior
                .find(isNotNull(unitRequestBody.getInitials()),
                        isNotNull(unitRequestBody.getDescription()));
        Map<String, Object> params = Params.getDefaultParams();
        params.put(Params.UNIT_DESCRIPTION, unitRequestBody.getDescription());
        params.put(Params.UNIT_INITIALS, unitRequestBody.getInitials());

        assert enumUnitSearchBehavior != null;
        Page<Unit> unitPage = enumUnitSearchBehavior.getUnitSearchBehavior()
                .searchPageUnit(unitRepository, params);

        if (!unitPage.getContent().isEmpty()) {
            if (unitPage.getContent().stream().anyMatch(x -> x.getInitials().equalsIgnoreCase(unitRequestBody.getInitials()))) {
                logger.error("Already exist unit initials {}", unitRequestBody.getInitials());
                throw new UnitAlreadyExistException(MessageFormat.format("Already exist unit initials {0}",
                        unitRequestBody.getInitials()));
            }
            if (unitPage.getContent().stream().anyMatch(x -> x.getDescription().equalsIgnoreCase(unitRequestBody.getDescription()))) {
                logger.error("Already exist unit description {}", unitRequestBody.getDescription());
                throw new UnitAlreadyExistException(MessageFormat.format("Already exist unit description {0} ",
                        unitRequestBody.getDescription()));
            }
        }

        logger.info("Preparing the object record in the database...");
        final Unit saved = this.unitRepository.save(unitToSave);
        logger.debug("Object saved successfully: {}", saved);

        logger.info("Preparing object conversion Unit to UnitDto");
        final UnitDto unitDto = this.unitMapper.unitToUnitDto(saved);
        logger.info("Object converted successfully");

        return unitDto;
    }

    @Override
    public UnitDto update(UnitRequestBody unitRequestBody) {
        logger.info("The update method was called");
        Unit unit = validateAndConvert(unitRequestBody);
        logger.info("Checking if Unit already exists");
        final Optional<Unit> optionalUnit = this.unitRepository.findById(unit.getId());
        if (optionalUnit.isEmpty()) {
            String message = MessageFormat.format("The unit with the id: {0} not exist", unit.getId());
            logger.error(message);
            throw new UnitNotExistException(message);
        }
        final Unit unitSaved = this.unitRepository.save(unit);
        return this.unitMapper.unitToUnitDto(unitSaved);
    }

    @Override
    public UnitDto delete(UUID id) {
        return null;
    }

    @Override
    public ResponseList<UnitDto> getByParams(Map<String, Object> params) {
        final EnumUnitSearchBehavior enumUnitSearchBehavior = EnumUnitSearchBehavior
                .find(isNotNull(params.get(Params.UNIT_INITIALS)),
                        isNotNull(params.get(Params.UNIT_DESCRIPTION)));
        assert enumUnitSearchBehavior != null;
        Page<Unit> pageUnit = enumUnitSearchBehavior.getUnitSearchBehavior().searchPageUnit(this.unitRepository, params);
        return this.unitMapper.pageUnitToResponseList(pageUnit);
    }

    @Override
    public Optional<UnitDto> getById(UUID id) {
        final Optional<Unit> unitOptional = this.unitRepository.findById(id);
        return unitOptional.map(this.unitMapper::unitToUnitDto);
    }


    private Unit validateAndConvert(UnitRequestBody unitRequestBody) {
        inputValidator.validate(unitRequestBody);
        logger.debug("Preparing object conversion UnitRequestBody to Unit. {}", unitRequestBody);
        Unit unit = this.unitMapper.unitRequestBodyToUnit(unitRequestBody);
        logger.info("Object converted successfully");
        return unit;
    }
}
