package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
}