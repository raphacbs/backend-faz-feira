package com.coelho.fazfeira.service;

import com.coelho.fazfeira.builder.UnitBuilder;
import com.coelho.fazfeira.builder.UnitRequestBodyBuilder;
import com.coelho.fazfeira.dto.UnitDto;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.excepitonhandler.ResourceValidationException;
import com.coelho.fazfeira.excepitonhandler.UnitAlreadyExistException;
import com.coelho.fazfeira.excepitonhandler.UnitNotExistException;
import com.coelho.fazfeira.model.Unit;
import com.coelho.fazfeira.repository.UnitRepository;
import com.coelho.fazfeira.validation.InputValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.validation.Validation;
import javax.validation.Validator;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
        when(unitRepository.findByDescriptionOrInitialsIgnoreCaseContaining(unitRequestBodyValid.getDescription(),
                unitRequestBodyValid.getInitials()))
                .thenReturn(List.of());
        final UnitDto saved = unitService.create(UnitRequestBodyBuilder.createValid());
        Assertions.assertNotNull(saved.getId());
    }

    @Test
    void givenUnitRequestBodyWithDescriptionAlreadyExist_whenSave_thenReturnException() {
        when(unitRepository.findByDescriptionOrInitialsIgnoreCaseContaining(unitRequestBodyDescriptionAlreadyExist.getDescription(),
                unitRequestBodyDescriptionAlreadyExist.getInitials()))
                .thenReturn(List.of(UnitBuilder
                        .createFromUnitRequestBodyWithDescriptionEmpty(unitRequestBodyDescriptionAlreadyExist)));
        Assertions.assertThrowsExactly(UnitAlreadyExistException.class,
                () -> unitService.create(unitRequestBodyDescriptionAlreadyExist),
                MessageFormat.format("Already exist unit description {0}",
                        unitRequestBodyDescriptionAlreadyExist.getDescription()));
    }

    @Test
    void givenUnitRequestBodyWithInitialsAlreadyExist_whenSave_thenReturnException() {
        Mockito.mock(InputValidator.class);
        when(unitRepository.findByDescriptionOrInitialsIgnoreCaseContaining(unitRequestBodyInitialsAlreadyExist.getDescription(),
                unitRequestBodyInitialsAlreadyExist.getInitials()))
                .thenReturn(List.of(UnitBuilder
                        .createFromUnitRequestBodyWithInitialsEmpty(unitRequestBodyInitialsAlreadyExist)));

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
        String message = MessageFormat.format("The unit with the id: {0} not exist", unitRequestBodyNotExist.getId().toString());

        when(unitRepository.findById(any()))
                .thenReturn(Optional.empty());

        UnitNotExistException unitNotExistException =
                Assertions.assertThrows(UnitNotExistException.class,
                        () -> unitService.update(unitRequestBodyNotExist));
        Assertions.assertTrue(unitNotExistException.getMessage().equalsIgnoreCase(message));
    }

    //TODO CRIAR TESTE PARA COBRIR AS VARIAÇÕES DOS PARÂMETROS ENVIADOS NAS REQUISIÇÕES

}
