package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Nota;

public interface NotaRepository extends JpaRepository<Nota, Integer> {

}
