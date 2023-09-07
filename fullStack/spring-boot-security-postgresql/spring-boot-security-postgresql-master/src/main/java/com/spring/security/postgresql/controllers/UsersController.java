package com.spring.security.postgresql.controllers;

import com.spring.security.postgresql.models.Stock;
import com.spring.security.postgresql.repository.StockRepository;
import com.spring.security.postgresql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class UsersController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/getAllStock")
    public List<Stock> getStock(){
        return stockRepository.findAll();

    }
}
