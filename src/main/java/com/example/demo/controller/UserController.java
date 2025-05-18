package com.example.demo.controller;

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

import com.example.demo.model.User;
import com.example.demo.repository.StatoUserRepository;
import com.example.demo.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/operatore")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatoUserRepository statoUserRepository;


    @GetMapping
    public String index(Model model,@RequestParam(name = "keyword", required = false) String email) {

        List<User> utentiList;
        
        if (email !=null && !email.isEmpty()) {
            utentiList=userRepository.findByEmailContainingIgnoreCase(email);
            
        }else {
            utentiList=userRepository.findAll();
        }

        model.addAttribute("utenti",utentiList);
        model.addAttribute("utentiObj", new User());
        model.addAttribute("stati", statoUserRepository.findAll());
        return "operatore/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
    model.addAttribute("utentiObj", new User());
    model.addAttribute("stati", statoUserRepository.findAll());
    return "operatore/create";
}


    @PostMapping("/create")
    public String store(Model model,
                       @Valid @ModelAttribute("utentiObj") User operatore,
                        BindingResult bindingResult) {

        model.addAttribute("stati", statoUserRepository.findAll());

        // se esiste già email nella nostra DB allora non inseriamo di nuovo come operatore
        if (userRepository.existsByEmail(operatore.getEmail())) {
            bindingResult.rejectValue("email", "error.utente", "Email già registrata!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("utenti", userRepository.findAll());
            return "operatore/create";
        }

        userRepository.save(operatore);
        return "redirect:/operatore";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isPresent()) {
            model.addAttribute("operatore", optUser.get());
            return "/operatore/show";
        }

        model.addAttribute("errorCause",
                "Non esiste un'operatore con id " + id);
        model.addAttribute("errorMessage",
                "Errore di ricerca dell'operatore");

        return "/errors/error";
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
    
        // Aggiorna i dati
        elementoUser.setEmail(operatore.getEmail());
        elementoUser.setPassword(operatore.getPassword());
        elementoUser.setStatoUser(operatore.getStatoUser());
    
        userRepository.save(elementoUser);
    
        return "redirect:/operatore";
    }
}
