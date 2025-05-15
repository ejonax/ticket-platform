package com.example.demo.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer ticket_id;

    @Column(nullable = false, length = 25)
    private String titolo;

    @Column(nullable = false, length = 65)
    private String descrizione;

    //user table
    @ManyToOne
    @JoinColumn(name = "creato_da_id", nullable = false)
    private User creato_da_id;

    //user table
    @ManyToOne
    @JoinColumn(name = "assegnato_a_id", nullable = false)
    private User assegnato_a_id;

    @Column(nullable = false)
    private Date data_apertura;

    @Column(nullable = false)
    private Date data_chiusura;

    //stato_ticket table
    @ManyToOne
    @JoinColumn(name = "stato_ticket_id", nullable = false)
    private StatoTicket stato_ticket_id;

    //nota table
    @OneToMany(mappedBy = "ticket")
    private List<Nota> note;

    //category-ticket table
    @ManyToMany
    @JoinTable(
        name = "Category_Ticket",
        joinColumns = @JoinColumn(name = "ticket_id"),
        inverseJoinColumns = @JoinColumn(name = "cat_id")
    )
    private List<Category> categories;

    public Integer getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(Integer ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public User getCreato_da_id() {
        return creato_da_id;
    }

    public void setCreato_da_id(User creato_da_id) {
        this.creato_da_id = creato_da_id;
    }

    public User getAssegnato_a_id() {
        return assegnato_a_id;
    }

    public void setAssegnato_a_id(User assegnato_a_id) {
        this.assegnato_a_id = assegnato_a_id;
    }

    public Date getData_apertura() {
        return data_apertura;
    }

    public void setData_apertura(Date data_apertura) {
        this.data_apertura = data_apertura;
    }

    public Date getData_chiusura() {
        return data_chiusura;
    }

    public void setData_chiusura(Date data_chiusura) {
        this.data_chiusura = data_chiusura;
    }

    public StatoTicket getStato_ticket_id() {
        return stato_ticket_id;
    }

    public void setStato_ticket_id(StatoTicket stato_ticket_id) {
        this.stato_ticket_id = stato_ticket_id;
    }

    public List<Nota> getNote() {
        return note;
    }

    public void setNote(List<Nota> note) {
        this.note = note;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    

}
