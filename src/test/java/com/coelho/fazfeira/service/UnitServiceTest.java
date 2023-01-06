package com.coelho.fazfeira.service;

import com.coelho.fazfeira.builder.UnitBuilder;
import com.coelho.fazfeira.builder.UnitParamsBuilder;
import com.coelho.fazfeira.builder.UnitRequestBodyBuilder;
import com.coelho.fazfeira.dto.ResponseList;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.excepitonhandler.ResourceValidationException;
import com.coelho.fazfeira.excepitonhandler.UnitAlreadyExistException;
import com.coelho.fazfeira.excepitonhandler.EntityNotExistException;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.Validation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


class UnitServiceTest {
    @InjectMocks
    private UnitService unitService;

    @InjectMocks
    private InputValidator<UnitRequestBody> inputValidator;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private InputValidator<UnitRequestBody> inputValidatorMock;

    @Mock
    private Validator validator;
    private Validator violationCreatorValidator;
    private final UnitRequestBody withoutInitials = UnitRequestBodyBuilder.createWithoutInitials();
    private final UnitRequestBody withoutDescription = UnitRequestBodyBuilder.createWithoutDescription();

    private Unit unitValid;
    private UnitRequestBody unitRequestBodyInitialsAlreadyExist;
    private UnitRequestBody unitRequestBodyDescriptionAlreadyExist;
    private UnitRequestBody unitRequestBodyValid;
    private UnitRequestBody unitRequestBodyNotExist;


    @BeforeEach
    void setup() {
        unitValid = UnitBuilder.createValid();
        unitRequestBodyInitialsAlreadyExist = UnitRequestBodyBuilder.createInitialsAlreadyExist();
        unitRequestBodyDescriptionAlreadyExist = UnitRequestBodyBuilder.createDescriptionAlreadyExist();
        unitRequestBodyValid = UnitRequestBodyBuilder.createValid();
        unitRequestBodyNotExist = UnitRequestBodyBuilder.createNotExist();
        violationCreatorValidator = Validation.buildDefaultValidatorFactory().getValidator();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValidUnitRequestBodyObject_whenSave_thenReturnSavedUnit() {

        when(unitRepository.save(any(Unit.class))).thenReturn(unitValid);
        when(unitRepository.findByDescriptionIgnoreCaseContainingAndInitialsIgnoreCaseContaining(any(Pageable.class),
                eq(unitRequestBodyValid.getDescription()),
                eq(unitRequestBodyValid.getInitials())))
                .thenReturn(new PageImpl<>(List.of()));
        final UnitDto saved = unitService.create(UnitRequestBodyBuilder.createValid());
        Assertions.assertNotNull(saved.getId());
    }

    @Test
    void givenUnitRequestBodyWithDescriptionAlreadyExist_whenSave_thenReturnException() {
        when(unitRepository.findByDescriptionIgnoreCaseContainingAndInitialsIgnoreCaseContaining(any(Pageable.class),
                eq(unitRequestBodyDescriptionAlreadyExist.getDescription()),
                eq(unitRequestBodyDescriptionAlreadyExist.getInitials())))
                .thenReturn(new PageImpl<>(List.of(UnitBuilder
                        .createFromUnitRequestBodyWithDescriptionEmpty(unitRequestBodyDescriptionAlreadyExist))));
        Assertions.assertThrowsExactly(UnitAlreadyExistException.class,
                () -> unitService.create(unitRequestBodyDescriptionAlreadyExist),
                MessageFormat.format("Already exist unit description {0}",
                        unitRequestBodyDescriptionAlreadyExist.getDescription()));
    }

    @Test
    void givenUnitRequestBodyWithInitialsAlreadyExist_whenSave_thenReturnException() {
        Mockito.mock(InputValidator.class);
        when(unitRepository.findByDescriptionIgnoreCaseContainingAndInitialsIgnoreCaseContaining(any(Pageable.class),
                eq(unitRequestBodyInitialsAlreadyExist.getDescription()),
                eq(unitRequestBodyInitialsAlreadyExist.getInitials())))
                .thenReturn(new PageImpl<>(List.of(UnitBuilder
                        .createFromUnitRequestBodyWithInitialsEmpty(unitRequestBodyInitialsAlreadyExist))));

        UnitAlreadyExistException unitAlreadyExistException = Assertions.assertThrows(UnitAlreadyExistException.class,
                () -> unitService.create(unitRequestBodyInitialsAlreadyExist));
        Assertions.assertEquals(MessageFormat.format("Already exist unit initials {0}",
                unitRequestBodyInitialsAlreadyExist.getInitials()), unitAlreadyExistException.getMessage());
    }

    @Test
    void givenUnitRequestBodyWithoutInitials_whenValidate_thenThrowException() {
        when(this.validator.validate(any()))
                .thenReturn(violationCreatorValidator.validate(withoutInitials));
        ResourceValidationException resourceValidationException =
                Assertions.assertThrows(ResourceValidationException.class,
                        () -> inputValidator.validate(withoutInitials));
        Assertions.assertTrue(resourceValidationException.getMessage().contains("Initials is mandatory"));
    }

    @Test
    void givenUnitRequestBodyWithoutDescription_whenValidate_thenThrowException() {
        when(this.validator.validate(any()))
                .thenReturn(violationCreatorValidator.validate(withoutDescription));
        ResourceValidationException resourceValidationException =
                Assertions.assertThrows(ResourceValidationException.class,
                        () -> inputValidator.validate(withoutDescription));
        Assertions.assertTrue(resourceValidationException.getMessage().contains("Description is mandatory"));
    }

    @Test
    void givenUnitRequestBodyValid_whenUpdate_thenReturnUnitUpdated() {
        when(unitRepository.save(any(Unit.class))).thenReturn(unitValid);
        when(unitRepository.findById(any()))
                .thenReturn(Optional.of(unitValid));
        final UnitDto saved = unitService.update(UnitRequestBodyBuilder.createValid());
        Assertions.assertNotNull(saved.getId());
    }

    @Test
    void givenUnitRequestBodyNotExist_whenUpdate_thenThrowException() {
        String message = MessageFormat.format("The unit with the id: {0} not exist", unitRequestBodyNotExist.getId());

        when(unitRepository.findById(any()))
                .thenReturn(Optional.empty());

        EntityNotExistException entityNotExistException =
                Assertions.assertThrows(EntityNotExistException.class,
                        () -> unitService.update(unitRequestBodyNotExist));
        Assertions.assertTrue(entityNotExistException.getMessage().equalsIgnoreCase(message));
    }

    @Test
    void givenUnitRequestBody_whenGetByDescriptionAndInitialsAlready_thenReturnResponseListUnitDto() {
        when(unitRepository.findByDescriptionIgnoreCaseContainingAndInitialsIgnoreCaseContaining(any(Pageable.class),
                eq(unitRequestBodyDescriptionAlreadyExist.getDescription()),
                eq(unitRequestBodyDescriptionAlreadyExist.getInitials())))
                .thenReturn(new PageImpl<>(List.of(UnitBuilder
                        .createFromUnitRequestBody(unitRequestBodyDescriptionAlreadyExist))));

        final ResponseList<UnitDto> units = unitService.getByParams(UnitParamsBuilder
                .builder()
                .description(unitRequestBodyDescriptionAlreadyExist.getDescription())
                .initials(unitRequestBodyDescriptionAlreadyExist.getInitials())
                .build()
                .create());

        Assertions.assertTrue(units.getTotalElements() > 0);
        Assertions.assertTrue(units.getItems().stream().anyMatch(x -> x.getDescription()
                .equalsIgnoreCase(unitRequestBodyDescriptionAlreadyExist.getDescription())
        ), "Not content searched unit");
        Assertions.assertTrue(units.getItems().stream().anyMatch(x -> x.getInitials()
                .equalsIgnoreCase(unitRequestBodyDescriptionAlreadyExist.getInitials())
        ), "Not content searched unit");
    }

    @Test
    void givenUnitRequestBody_whenGetByDescriptionAlready_thenReturnResponseListUnitDto() {
        when(unitRepository.findByDescriptionIgnoreCaseContaining(any(Pageable.class),
                eq(unitRequestBodyDescriptionAlreadyExist.getDescription())))
                .thenReturn(new PageImpl<>(List.of(UnitBuilder
                        .createFromUnitRequestBody(unitRequestBodyDescriptionAlreadyExist))));

        final ResponseList<UnitDto> units = unitService.getByParams(UnitParamsBuilder
                .builder()
                .description(unitRequestBodyDescriptionAlreadyExist.getDescription())
                .initials(null)
                .build()
                .create());

        Assertions.assertTrue(units.getTotalElements() > 0);
        Assertions.assertTrue(units.getItems().stream().anyMatch(x -> x.getDescription()
                .equalsIgnoreCase(unitRequestBodyDescriptionAlreadyExist.getDescription())
        ), "Not content searched unit");
    }

    @Test
    void givenUnitRequestBody_whenGetByInitialsAlready_thenReturnResponseListUnitDto() {
        when(unitRepository.findByInitialsIgnoreCaseContaining(any(Pageable.class),
                eq(unitRequestBodyDescriptionAlreadyExist.getInitials())))
                .thenReturn(new PageImpl<>(List.of(UnitBuilder
                        .createFromUnitRequestBody(unitRequestBodyDescriptionAlreadyExist))));

        final ResponseList<UnitDto> units = unitService.getByParams(UnitParamsBuilder
                .builder()
                .description(null)
                .initials(unitRequestBodyDescriptionAlreadyExist.getInitials())
                .build()
                .create());

        Assertions.assertTrue(units.getTotalElements() > 0);
        Assertions.assertTrue(units.getItems().stream().anyMatch(x -> x.getInitials()
                .equalsIgnoreCase(unitRequestBodyDescriptionAlreadyExist.getInitials())
        ), "Not content searched unit");
    }

    @Test
    void givenUnitRequestBody_whenGetAll_thenReturnResponseListUnitDto() {
        when(unitRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        Collections.nCopies(10,
                                UnitBuilder.createValid())
                ));

        final ResponseList<UnitDto> units = unitService.getByParams(UnitParamsBuilder
                .builder()
                .description(null)
                .initials(null)
                .build()
                .create());

        Assertions.assertEquals(10, units.getTotalElements());

    }

    @Test
    void givenUnitId_whenGetById_thenReturnResponseListUnitDto() {
        final Unit valid = UnitBuilder.createValid();
        when(unitRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(valid));

        final Optional<UnitDto> unitDto = unitService.getById(valid.getId());

        Assertions.assertTrue(unitDto.isPresent());

    }


}
