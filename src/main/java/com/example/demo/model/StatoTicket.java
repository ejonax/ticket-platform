package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stato_Ticket")
public class StatoTicket {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer stato_ticket_id;

    @Column(nullable = false, length = 50)
    private String stato_description;

    //ticket table
    @OneToMany(mappedBy = "stato_ticket_id")
    private List<Ticket> tickets;


    public Integer getStato_ticket_id() {
        return stato_ticket_id;
    }


    public void setStato_ticket_id(Integer stato_ticket_id) {
        this.stato_ticket_id = stato_ticket_id;
    }


    public String getStato_description() {
        return stato_description;
    }


    public void setStato_description(String stato_description) {
        this.stato_description = stato_description;
    }


    public List<Ticket> getTickets() {
        return tickets;
    }


    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    
}
