package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer userId;

    @Email
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String password;

    //stato-user table
    @ManyToOne
    @JoinColumn(name = "stato_user_id", nullable = false)
    private StatoUser statoUser;

    //urser-role table
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "User_Role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    //nota table
    @OneToMany(mappedBy = "autore")
    private List<Nota> noteCreate;

    //ticket table
    @OneToMany(mappedBy = "creatoDaId")
    private List<Ticket> ticketCreati;

    //ticket table
    @OneToMany(mappedBy = "assegnatoAId")
    private List<Ticket> ticketAssegnati;

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatoUser getStatoUser() {
        return statoUser;
    }

    public void setStatoUser(StatoUser statoUser) {
        this.statoUser = statoUser;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Nota> getNoteCreate() {
        return noteCreate;
    }

    public void setNoteCreate(List<Nota> noteCreate) {
        this.noteCreate = noteCreate;
    }

    public List<Ticket> getTicketCreati() {
        return ticketCreati;
    }

    public void setTicketCreati(List<Ticket> ticketCreati) {
        this.ticketCreati = ticketCreati;
    }

    public List<Ticket> getTicketAssegnati() {
        return ticketAssegnati;
    }

    public void setTicketAssegnati(List<Ticket> ticketAssegnati) {
        this.ticketAssegnati = ticketAssegnati;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
}
