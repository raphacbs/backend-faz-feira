package com.coelho.fazfeira.validation;

import com.coelho.fazfeira.excepitonhandler.ResourceValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class InputValidator<T> {

    @Autowired
    private final Validator validator;

    private static Logger logger = LoggerFactory.getLogger(InputValidator.class);

    public InputValidator(Validator validator) {
        this.validator = validator;
    }

    public void validate(T input) {
        logger.info("Begin input validate");
        Set<ConstraintViolation<T>> violations = validator.validate(input);
        if (!violations.isEmpty()) {
            List<String> messages = new ArrayList<>();
            for (ConstraintViolation<T> constraintViolation : violations) {
                messages.add(constraintViolation.getMessage());
            }
            String message = String.format("Error occurred: %s ",  String.join(" and ", messages));
            logger.error(message);
            throw new ResourceValidationException(message);
        }
        logger.debug("Input of type '{}' validate with success", input.getClass().getName());
    }
}
