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
@Table(name = "Stato_User")
public class StatoUser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer stato_user_id;

    @Column(nullable = false, length = 50)
    private String statoDescription;

    //user table
    @OneToMany(mappedBy = "statoUser")
    private List<User> users;

    public Integer getStato_user_id() {
        return stato_user_id;
    }

    public void setStato_user_id(Integer stato_user_id) {
        this.stato_user_id = stato_user_id;
    }

    public String getStatoDescription() {
        return statoDescription;
    }

    public void setStatoDescription(String statoDescription) {
        this.statoDescription = statoDescription;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
