package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
   
    boolean existsByTitolo(String titolo);

    List<Ticket> findByTitoloContainingIgnoreCase(String titolo);

    List<Ticket> findByAssegnatoAId_UserId(Integer userId);
}
