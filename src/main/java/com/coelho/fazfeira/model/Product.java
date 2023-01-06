package com.coelho.fazfeira.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "product")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Column(name = "code")
    private String code;
    @Column(nullable = false)
    private String description;
    @Column
    private String brand;
    @Column
    private String thumbnail;
    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updateAt;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

}