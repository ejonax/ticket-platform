package com.example.demo.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Ticket;
import com.example.demo.repository.TicketRepository;

@RestController
@RequestMapping("/api/ticket")
public class TicketRestController {

    @Autowired
    private TicketRepository ticketRepository;

    // lista delle ticket
    @GetMapping
    public List<Ticket> index( @RequestParam (name="keyword", required=false) String param) 
    {
        List<Ticket> tickets;
    
        if (param != null && !param.isEmpty()) {
                tickets = ticketRepository.findByTitoloContainingIgnoreCase(param);
        } else {
                tickets = ticketRepository.findAll();
        }

        return tickets;
    }
    
    
    // lista delle tickets filtrate per categoria
    @GetMapping("/categoria/{param}")
    public List<Ticket> getByCategoria(@PathVariable String param) {
        return ticketRepository.findByCategories_catNomeIgnoreCase(param);
    }

    // lista delle tickets filtrate per statoTicket
    @GetMapping("/stato/{stato}")
    public List<Ticket> getByStato(@PathVariable String stato) {
        return ticketRepository.findByStatoTicketId_statoDescriptionIgnoreCase(stato);
    }

      // lista delle tickets filtrate per statoTicket
      @GetMapping("/stato2/{stato}")
      public List<Ticket> getByStato2(@PathVariable String stato) {
          return ticketRepository.findByStatoTicketId_statoDescriptionIgnoreCase2(stato);
      }
}
