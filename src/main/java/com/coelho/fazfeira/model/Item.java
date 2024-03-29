package com.coelho.fazfeira.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item implements  IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column
    private String note;
    @Column(nullable = false)
    private Double quantity;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Double perUnit;
    @Column(nullable = false)
    private boolean isAdded;
    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}