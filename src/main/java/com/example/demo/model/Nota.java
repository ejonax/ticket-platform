package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Nota")
public class Nota {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer notaId;

    @Column(nullable = false, length = 65)
    private String descrizione;

    @Column(nullable = false)
    private LocalDate dataNota;

    // user table
    @ManyToOne
    @JoinColumn(name = "autore_id", nullable = false)
    private User autore;

    //ticket table
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    public Integer getNotaId() {
        return notaId;
    }

    public void setNotaId(Integer notaId) {
        this.notaId = notaId;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataNota() {
        return dataNota;
    }

    public void setDataNota(LocalDate dataNota) {
        this.dataNota = dataNota;
    }

    public User getAutore() {
        return autore;
    }

    public void setAutore(User autore) {
        this.autore = autore;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

   
   
}
