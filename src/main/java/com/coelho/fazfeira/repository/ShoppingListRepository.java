package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.ShoppingList;
import com.coelho.fazfeira.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, UUID> {
    Page<ShoppingList> findByUserAndDescriptionIgnoreCaseContaining(Pageable pageable, User user, String description);
    Page<ShoppingList> findByUserAndDescriptionIgnoreCaseContainingAndIsOpen(Pageable pageable, User user, String description, boolean isOpen);
    Page<ShoppingList> findByUser(Pageable pageable, User user);
    Page<ShoppingList> findByUserAndIsOpen(Pageable pageable, User user, boolean isOpen);
}