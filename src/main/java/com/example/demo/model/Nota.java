package com.example.demo.model;

import java.util.Date;

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
    private Integer nota_id;

    @Column(nullable = false, length = 65)
    private String descrizione;

    @Column(nullable = false)
    private Date data_nota;

    // user table
    @ManyToOne
    @JoinColumn(name = "autore_id", nullable = false)
    private User autore;

    //ticket table
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    public Integer getNota_id() {
        return nota_id;
    }

    public void setNota_id(Integer nota_id) {
        this.nota_id = nota_id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData_nota() {
        return data_nota;
    }

    public void setData_nota(Date data_nota) {
        this.data_nota = data_nota;
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
