package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.model.ShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    Page<Item> findByShoppingList(Pageable pageable, ShoppingList shoppingList);

    Page<Item> findByShoppingListAndIsAdded(Pageable pageable, ShoppingList shoppingList, boolean isAdded);

    Optional<Item> findByShoppingListAndProduct(ShoppingList shoppingList, Product product);

    @Query("select i from Item i " +
            "inner join Product p ON p.code = i.product.code " +
            "inner join ShoppingList sl on sl.id  = i.shoppingList.id " +
            "where i.product.description like CONCAT('%',UPPER(:productDesc),'%') and sl.id = :shoppingListId")
    Page<Item> findByShoppingListIdAndProductDesc(Pageable pageable,
                                                  @Param("shoppingListId") UUID shoppingListId,
                                                  @Param("productDesc") String productDesc);

    @Query("select i from Item i " +
            "inner join Product p ON p.code = i.product.code " +
            "inner join ShoppingList sl on sl.id  = i.shoppingList.id " +
            "where i.product.description like CONCAT('%',UPPER(:productDesc),'%') " +
            "and sl.id = :shoppingListId " +
            "and i.isAdded = :isAdded")
    Page<Item> findByShoppingListIdAndProductDescAndIsAdded(Pageable pageable,
                                                            @Param("shoppingListId") UUID shoppingListId,
                                                            @Param("productDesc") String productDesc,
                                                            @Param("isAdded") boolean isAdded);
}