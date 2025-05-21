package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
   
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    /*controlliamo se email esiste gia nella lista degli email della DB */
    List<User> findByEmailContainingIgnoreCase(String keyword);

    /* serve per trovare la lista degli user disponibili cosi gli assegnamo a dei ticket nuovi */
    List<User> findByStatoUser_StatoDescription(String statoDescription);
}
