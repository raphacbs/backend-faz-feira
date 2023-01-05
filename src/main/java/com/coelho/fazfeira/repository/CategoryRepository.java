package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}