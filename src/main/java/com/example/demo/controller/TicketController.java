package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.StatoTicketRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketController {


    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private StatoTicketRepository statoTicketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

   
    @GetMapping
    public String index(Model model,
                        @RequestParam (name="keyword", required=false) String titolo) 
    {
        List<Ticket> tickets;

        if (titolo !=null && !titolo.isEmpty()) {
            tickets=ticketRepository.findByTitoloContainingIgnoreCase(titolo);
            
        } else {
            tickets = ticketRepository.findAll();
        }


        model.addAttribute("tickets", tickets);
        return "ticket/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        model.addAttribute("ticket", ticket);
        return "/ticket/show"; // Nome della vista per i dettagli
    }

    @GetMapping("/create")
    public String create(Model model) {

        Ticket ticket = new Ticket();
        LocalDate oggi = LocalDate.now();
        ticket.setDataApertura(oggi);
        ticket.setDataChiusura(oggi.plusDays(30));

        model.addAttribute("ticket", new Ticket());
        model.addAttribute("stati", statoTicketRepository.findAll());
        model.addAttribute("userDisponibili", userRepository.findByStatoUser_StatoDescription("Disponibile"));
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("categorie", categoryRepository.findAll());
        return "ticket/create";
    }

    @PostMapping ("/create")
    public String store(@Valid @ModelAttribute Ticket ticket,
                       BindingResult bindingResult,
                       Model model) {

    // il titolo non deve esistere in DB
    if (ticketRepository.existsByTitolo(ticket.getTitolo())) {
        bindingResult.rejectValue("titolo", "invalid", "Un ticket con questo titolo esiste già");
    }

    // se la data della creazione non si mette nella form allora meteremo la data di oggi
    LocalDate oggi = LocalDate.now();

    if (ticket.getDataApertura() != null) {
        if (ticket.getDataApertura().isAfter(oggi)) {
            bindingResult.rejectValue("dataApertura", "invalid", "La data di apertura non può essere nel futuro");
        }
    }

    // se le due date, dataApertura e dataChiusura si mettono dall'operatore nella for create-ticket
    if (ticket.getDataApertura() != null && ticket.getDataChiusura() != null) {
        if (ticket.getDataChiusura().isBefore(ticket.getDataApertura())) {
            bindingResult.rejectValue("dataChiusura", "invalid", "La data di chiusura non può essere prima della data di apertura");
        }
    }

    // se si generano degli errori allora ricarichiamo la forma create-ticket
    if (bindingResult.hasErrors()) {
        model.addAttribute("stati", statoTicketRepository.findAll());
        model.addAttribute("userDisponibili", userRepository.findByStatoUser_StatoDescription("DISPONIBILE"));
        model.addAttribute("allUsers", userRepository.findAll());
        return "ticket/create";
    }

    // se l'operatore non mette/sceglie le date apertura e chiusura allora oggi diventa la data apertura e la data chiusura si mette oggi+30
    if (ticket.getDataApertura() == null) {
        ticket.setDataApertura(oggi);
    }
    if (ticket.getDataChiusura() == null) {
        ticket.setDataChiusura(oggi.plusDays(30));
    }

    ticketRepository.save(ticket);
    return "redirect:/ticket";
}
    

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(id).get());
        model.addAttribute("stati", statoTicketRepository.findAll());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("userDisponibili", userRepository.findByStatoUser_StatoDescription("DISPONIBILE"));
        model.addAttribute("categories", categoryRepository.findAll());
        return "ticket/edit";
    }

    
    @PostMapping("/edit/{id}")
    public String update(  @PathVariable("id") Integer id,
                           @Valid @ModelAttribute("ticket") Ticket ticketForm,
                           BindingResult bindingResult,
                           Model model) 
    {   // data apertura e data chiusura non devono esser NULL
        if (ticketForm.getDataApertura() != null && ticketForm.getDataChiusura() != null) {
             //data apertura deve essere prima della data di chiusura
            if (!ticketForm.getDataApertura().isBefore(ticketForm.getDataChiusura())) {
                bindingResult.rejectValue("dataApertura", null, "La data di apertura deve essere prima della data di chiusura");
            }

            /* non vale come controllo perchè se vogliamo cambiare solo l'operatore ma non le date allora si genere l'errore delle date */
            /*
            // data chiusura non più essere prima di oggi ( che è la data do oggi che si fa l'edit)
            if (ticketForm.getDataChiusura().isBefore(LocalDate.now())) {
                bindingResult.rejectValue("dataChiusura", null, "La data di chiusura non può essere nel passato");
            }
            */
        }


        Optional<User> assegnato = userRepository.findById(ticketForm.getAssegnatoAId().getUserId());
        if (assegnato.isEmpty()) {
            bindingResult.rejectValue("assegnatoAId", null, "Seleziona un operatore disponibile");
        }
    
        if (bindingResult.hasErrors()) {
            model.addAttribute("stati", statoTicketRepository.findAll());
            model.addAttribute("userDisponibili", userRepository.findByStatoUser_StatoDescription("DISPONIBILE"));
            model.addAttribute("allUsers", userRepository.findAll());
            return "ticket/edit";
        }
        
        ticketRepository.save(ticketForm);
        return "redirect:/ticket";
    }


     /* START delete session */
    @PostMapping("/delete/{id}")
    public String deleteTicket(@PathVariable Integer id) {
        ticketRepository.deleteById(id);
        return "redirect:/ticket";
    }
     /* END delete session */
}

