package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.StatoUser;


public interface StatoUserRepository extends JpaRepository<StatoUser, Integer> {
  
    
}
