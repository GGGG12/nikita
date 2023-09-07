package com.spring.security.postgresql.repository;

import com.spring.security.postgresql.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findById(Stock id);
    List<Stock> findByPhone(String phone);


}
