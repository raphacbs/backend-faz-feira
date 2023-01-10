package com.coelho.fazfeira.model;

import lombok.*;
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
@Builder
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