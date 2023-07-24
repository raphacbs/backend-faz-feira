package com.coelho.fazfeira.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceHistory implements Serializable, IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "supermarket_id")
    private Supermarket supermarket;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

}