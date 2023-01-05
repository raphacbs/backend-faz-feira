package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupermarketRepository extends JpaRepository<Supermarket, UUID> {
}