package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnitRepository extends JpaRepository<Unit, UUID> {
    Optional<Unit> findByDescriptionIgnoreCaseContaining(String description);

    Optional<Unit> findByInitialsIgnoreCaseContaining(String initials);

    Page<Unit> findByDescriptionIgnoreCaseContaining(Pageable pageable, String description);

    Page<Unit> findByInitialsIgnoreCaseContaining(Pageable pageable, String initials);

    List<Unit> findByDescriptionOrInitialsIgnoreCaseContaining(String description, String initials);

    Page<Unit> findByDescriptionOrInitialsIgnoreCaseContaining(Pageable pageable, String description, String initials);
    Page<Unit> findByDescriptionIgnoreCaseContainingAndInitialsIgnoreCaseContaining(Pageable pageable, String description, String initials);

}