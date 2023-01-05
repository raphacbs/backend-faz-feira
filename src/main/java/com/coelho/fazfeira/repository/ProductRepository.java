package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}