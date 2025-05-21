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
    private Integer statoTicketId;

    @Column(nullable = false, length = 50)
    private String statoDescription;

    //ticket table
    @OneToMany(mappedBy = "statoTicketId")
    private List<Ticket> tickets;

    public Integer getStatoTicketId() {
        return statoTicketId;
    }

    public void setStatoTicketId(Integer statoTicketId) {
        this.statoTicketId = statoTicketId;
    }

    public String getStatoDescription() {
        return statoDescription;
    }

    public void setStatoDescription(String statoDescription) {
        this.statoDescription = statoDescription;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }


  
    
}
