package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByDescriptionIgnoreCaseContaining(Pageable pageable, String description);
    Optional<Product> findByCode(String code);
}