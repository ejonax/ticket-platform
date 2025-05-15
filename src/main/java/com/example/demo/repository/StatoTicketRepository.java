package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.StatoTicket;

public interface StatoTicketRepository extends JpaRepository<StatoTicket, Integer> {
    
}

