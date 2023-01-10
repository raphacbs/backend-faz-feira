package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.PriceHistory;
import com.coelho.fazfeira.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, UUID> {
    Page<PriceHistory> findByProduct(Pageable pageable, Product product);
}