package com.coelho.fazfeira.validation;

import com.coelho.fazfeira.builder.UnitRequestBodyBuilder;
import com.coelho.fazfeira.dto.UnitRequestBody;
import com.coelho.fazfeira.excepitonhandler.ResourceValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class InputValidatorTest {

    @InjectMocks
    private InputValidator<UnitRequestBody> inputValidator;

    @Mock
    private Validator validator;


    private Validator violationCreatorValidator;

    private final UnitRequestBody unitRequestBodyInvalid = UnitRequestBodyBuilder.createWithoutInitials();
    private final UnitRequestBody unitRequestBodyValid = UnitRequestBodyBuilder.createValid();

    @BeforeEach
    void setup() {
        violationCreatorValidator = Validation.buildDefaultValidatorFactory().getValidator();
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void givenUnitRequestBodyWithoutInitials_whenValidate_thenThrowException() {
        when(this.validator.validate(any()))
                .thenReturn(violationCreatorValidator.validate(unitRequestBodyInvalid));
        ResourceValidationException resourceValidationException =
                Assertions.assertThrows(ResourceValidationException.class,
                        () -> inputValidator.validate(unitRequestBodyInvalid));
        Assertions.assertTrue(resourceValidationException.getMessage().contains("Error occurred:"));
    }

    @Test
    void givenUnitRequestBodyValid_whenValidate_thenNotThrowException() {
        when(this.validator.validate(any()))
                .thenReturn(violationCreatorValidator.validate(unitRequestBodyValid));
        Assertions.assertDoesNotThrow(() -> inputValidator.validate(unitRequestBodyValid));
    }

}
