package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Ticket;
import com.example.demo.model.User;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
   
    boolean existsByTitolo(String titolo);

    List<Ticket> findByTitoloContainingIgnoreCase(String titolo);

    List<Ticket> findByAssegnatoAId_UserId(Integer userId);

    List<Ticket> findByAssegnatoAId_Email(String email);

    List<Ticket> findByTitoloContainingIgnoreCaseAndAssegnatoAId_Email(String titolo, String email);

    List<Ticket> findByAssegnatoAIdAndStatoTicketId_statoDescriptionIn(User elementoUser, List<String> statiAttivi);


}
