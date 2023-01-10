package com.coelho.fazfeira.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory {
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

}