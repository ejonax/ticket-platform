package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Nota;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.NotaRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/nota")
public class NotaController {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

  @PostMapping("/create")
public String create(@RequestParam("ticketId") Integer ticketId,
                     @Valid @ModelAttribute("nota") Nota nota,
                     BindingResult bindingResult,
                     Model model) {

    // Recupera il ticket esistente dal DB
    Ticket ticket = ticketRepository.findById(ticketId)
        .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));

    // Imposta il ticket nella nota
    nota.setTicket(ticket);

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String email = auth.getName();
    User userLoggato = userRepository.findByEmail(email).orElseThrow();
   
    nota.setAutore(userLoggato);
    nota.setDataNota(LocalDate.now());
    notaRepository.save(nota);

    return "redirect:/ticket/show/" + ticketId;
}

    
}
