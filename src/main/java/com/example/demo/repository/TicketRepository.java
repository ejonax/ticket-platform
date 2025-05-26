package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Ticket;
import com.example.demo.model.User;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
   
    boolean existsByTitolo(String titolo);

    List<Ticket> findByTitoloContainingIgnoreCase(String titolo);

    List<Ticket> findByAssegnatoAId_UserId(Integer userId);

    List<Ticket> findByAssegnatoAId_Email(String email);

    List<Ticket> findByTitoloContainingIgnoreCaseAndAssegnatoAId_Email(String titolo, String email);

    List<Ticket> findByAssegnatoAIdAndStatoTicketId_statoDescriptionIn(User elementoUser, List<String> statiAttivi);

    //usato da RestController
    List<Ticket> findByCategories_catNomeIgnoreCase(String categoria);
    List<Ticket> findByStatoTicketId_statoDescriptionIgnoreCase(String stato);

    @Query(value = "SELECT t.* FROM ticket t JOIN stato_ticket s ON t.stato_ticket_id = s.stato_ticket_id WHERE LOWER(s.stato_description) = LOWER(:stato)", nativeQuery = true)
    List<Ticket> findByStatoTicketId_statoDescriptionIgnoreCase2(@Param("stato") String stato);


}
