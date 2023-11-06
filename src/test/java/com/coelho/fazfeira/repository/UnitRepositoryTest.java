package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.builder.UnitBuilder;
import com.coelho.fazfeira.model.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitRepositoryTest {

    @Autowired
    UnitRepository unitRepository;

    @Test
    void givenUnitObject_whenSave_thenReturnSavedUnit() {
        Unit unitToSave = UnitBuilder.createValid();
        final Unit savedUnit = unitRepository.save(unitToSave);
        assertNotNull(savedUnit);
        assertTrue(Pattern.matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})", savedUnit.getId().toString()));
    }

    @Test
    void givenUnitObjectNull_whenSave_thenReturnException() {
        Unit unit = null;
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> unitRepository.save(unit));
    }

}
