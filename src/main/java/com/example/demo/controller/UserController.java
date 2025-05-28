package com.example.demo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StatoUserRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/operatore")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatoUserRepository statoUserRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoleRepository roleRepository;


    @GetMapping
    public String index(Model model,
                       @RequestParam(name = "keyword", required = false) String email,
                        Authentication authentication) {

        String userLoggato = authentication.getName(); // ottieni email loggato
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<User> utentiList;

        boolean isAdmin = false;
        for (GrantedAuthority role : authorities) {
            if (role.getAuthority().equals("admin")) {
                isAdmin = true;
                 break;
            }
        }
        
        if (isAdmin) {
            // nel caso dell'admin lista tutti gli operatori
            if (email != null && !email.isEmpty()) {
                utentiList = userRepository.findByEmailContainingIgnoreCase(email);
            } else {
                utentiList = userRepository.findAll();
            }
        } else {
            // nel caso dell'oper lista solo se stesso
            Optional<User> currentUser = userRepository.findByEmail(userLoggato);
            if (currentUser.isPresent()) {
                  utentiList = List.of(currentUser.get());
            } else {
                utentiList = List.of();
            }

        }

        model.addAttribute("utenti",utentiList);
        model.addAttribute("utentiObj", new User());
        model.addAttribute("stati", statoUserRepository.findAll());
        model.addAttribute("ruoli", roleRepository.findAll());
        return "operatore/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
    model.addAttribute("utentiObj", new User());
    model.addAttribute("stati", statoUserRepository.findAll());
    model.addAttribute("ruoli", roleRepository.findAll());
    return "operatore/create";
}


    @PostMapping("/create")
    public String store(Model model,
                       @Valid @ModelAttribute("utentiObj") User operatore,
                        BindingResult bindingResult) {

        model.addAttribute("stati", statoUserRepository.findAll());
        model.addAttribute("ruoli", roleRepository.findAll());

        // se esiste già email nella nostra DB allora non inseriamo di nuovo come operatore
        if (userRepository.existsByEmail(operatore.getEmail())) {
            bindingResult.rejectValue("email", "error.utente", "Email già registrata!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("utenti", userRepository.findAll());
            return "operatore/create";
        }

        if (operatore.getRoles() == null || operatore.getRoles().isEmpty()) {
            bindingResult.rejectValue("roles", "error.utente", "Devi selezionare un ruolo.");
            return "operatore/create";
        }

        userRepository.save(operatore);
        return "redirect:/operatore";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            return "redirect:/";
        }


        List<Ticket> ticketList = ticketRepository.findByAssegnatoAId_UserId(optUser.get().getUserId());
    
        model.addAttribute("user", optUser.get());
        model.addAttribute("tickets", ticketList);
        model.addAttribute("ruoli", roleRepository.findAll());
    
        return "operatore/show";
       
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        userRepository.deleteById(id);
        return "redirect:/operatore";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) {
            model.addAttribute("operatore", optUser.get());
            model.addAttribute("stati", statoUserRepository.findAll()); 
            model.addAttribute("ruoli", roleRepository.findAll());
            return "operatore/edit";
        }

        model.addAttribute("errorMessage", "Operatore non trovato con id: " + id);
        return "operatore/edit";
    }

    /*Modificare un operatore della lista degli operatori */
    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute("operatore") @Valid User operatore,
                          BindingResult result,
                          Model model) {
    
        model.addAttribute("stati", statoUserRepository.findAll());
        model.addAttribute("ruoli", roleRepository.findAll());
        Optional<User> userByIDOptional = userRepository.findById(id);
    
        if (userByIDOptional.isEmpty()) {
            model.addAttribute("errorMessage", "Operatore non trovato.");
            return "errors/error";
        }
    
        User elementoUser = userByIDOptional.get();
    
        // if l'email è usata da un altro utente
        Optional<User> userBySameEmail = userRepository.findByEmail(operatore.getEmail());
        if (userBySameEmail.isPresent() && !userBySameEmail.get().getUserId().equals(id)) {
            result.rejectValue("email", "error.operatore", "Email già utilizzata da un altro operatore!");
            return "operatore/edit";
        }
    
        if (result.hasErrors()) {
            model.addAttribute("stati", statoUserRepository.findAll());
            return "operatore/edit";
        }
    
       

        // se ci sono ticket con lo stato "da fare","in corso" allora non salvare il nuovo stato dell'operatore
        String nuovoStato = operatore.getStatoUser().getStatoDescription();
        if ("non attivo".equalsIgnoreCase(nuovoStato)) {
            List<String> statiAttivi = List.of("da fare", "in corso");
    
            List<Ticket> ticketAttivi = ticketRepository.findByAssegnatoAIdAndStatoTicketId_statoDescriptionIn(elementoUser, statiAttivi);
            if (!ticketAttivi.isEmpty()) {
                result.rejectValue("statoUser", "error.operatore", "Non puoi impostare lo stato a 'non attivo' finché ci sono ticket in corso o da fare.");
                return "operatore/edit";
            }
        }

        // Aggiorna i dati
        elementoUser.setEmail(operatore.getEmail());
        elementoUser.setPassword(operatore.getPassword());
        elementoUser.setStatoUser(operatore.getStatoUser());
    
        userRepository.save(elementoUser);
    
        return "redirect:/operatore";
    }
}
