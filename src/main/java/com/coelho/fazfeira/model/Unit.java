package com.coelho.fazfeira.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "unit")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "description", unique = true, nullable = false)
    private String description;
    @Column(name = "initials", unique = true, nullable = false)
    private String initials;
    @Column(name = "is_integer_type", nullable = false, columnDefinition = "boolean default true")
    private boolean isIntegerType;

}