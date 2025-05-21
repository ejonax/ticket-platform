package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer ticketId;

    @NotBlank
    @Column(nullable = false, length = 25)
    private String titolo;

    @NotBlank
    @Column(nullable = false, length = 65)
    private String descrizione;

    //user table
    @NotNull(message = "Devi selezionare un operatore creato da.")
    @ManyToOne
    @JoinColumn(name = "creato_da_id", nullable = false)
    private User creatoDaId;

    //user table
    @Valid
    @NotNull(message = "Devi selezionare un operatore")
    @ManyToOne
    @JoinColumn(name = "assegnato_a_id", nullable = false)
    private User assegnatoAId;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataApertura;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataChiusura;

    //stato_ticket table
    @ManyToOne
    @JoinColumn(name = "stato_ticket_id", nullable = false)
    private StatoTicket statoTicketId;

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

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
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

    public User getCreatoDaId() {
        return creatoDaId;
    }

    public void setCreatoDaId(User creatoDaId) {
        this.creatoDaId = creatoDaId;
    }

    public User getAssegnatoAId() {
        return assegnatoAId;
    }

    public void setAssegnatoAId(User assegnatoAId) {
        this.assegnatoAId = assegnatoAId;
    }

    public LocalDate getDataApertura() {
        return dataApertura;
    }

    public void setDataApertura(LocalDate dataApertura) {
        this.dataApertura = dataApertura;
    }

    public LocalDate getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(LocalDate dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public StatoTicket getStatoTicketId() {
        return statoTicketId;
    }

    public void setStatoTicketId(StatoTicket statoTicketId) {
        this.statoTicketId = statoTicketId;
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
