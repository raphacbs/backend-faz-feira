package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.model.Item;
import com.coelho.fazfeira.model.PriceHistory;
import com.coelho.fazfeira.model.Product;
import com.coelho.fazfeira.model.ShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, UUID> {
    Page<PriceHistory> findByProduct(Pageable pageable, Product product);

    Page<PriceHistory> findByProductAndItem(Pageable pageable, Product product, Item item);

    Page<PriceHistory> findByProductAndItemAndShoppingList(Pageable pageable, Product product, Item item, ShoppingList list);

    Optional<PriceHistory> findByProductAndItemAndShoppingList(Product product, Item item, ShoppingList list);

    Page<PriceHistory> findByProductAndItemNotAndShoppingList(Pageable pageable, Product product, Item item, ShoppingList list);

    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) *" +
            " cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(s.latitude))))";

//    @Query("select ph from PriceHistory ph " +
//            " where ph.product.code = :productCode " +
//            "and (ph.item.id  <> :itemId " +
//            "or ph.item.id is null) " +
//            "order by ph.updatedAt desc ")
    @Query(value ="select * from price_history ph " +
            "where  ph.product_code  = :productCode " +
            "and (ph.item_id  <> :itemId " +
            "or ph.item_id   is null) " +
            "order by updated_at  desc limit :count", nativeQuery=true)
    List<PriceHistory> findWithProductNoItem(@Param("productCode") String productCode,
    @Param("itemId") UUID itemId,
    @Param("count") int count);


}