package com.coelho.fazfeira;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "error_code")
public class ErrorCode {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "message", nullable = false)
    private String message;

}