package com.coelho.fazfeira.repository;

import com.coelho.fazfeira.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorCodeRepository extends JpaRepository<ErrorCode, Integer> {
}