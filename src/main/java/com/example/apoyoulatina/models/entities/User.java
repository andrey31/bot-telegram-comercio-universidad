package com.example.apoyoulatina.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * User
 */
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String user;

    private String pass;

    private String name;

    private String lastName;

    private String mail;

    private String identification;

    @JoinColumn(name = "user_telegram_id")
    @OneToOne
    private UserTelegram userTelegram;


    public User() {
    }

    public User(long id, String user, String pass, String name, String lastName, String mail, String identification, UserTelegram userTelegram) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.identification = identification;
        this.userTelegram = userTelegram;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIdentification() {
        return this.identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public UserTelegram getUserTelegram() {
        return this.userTelegram;
    }

    public void setUserTelegram(UserTelegram userTelegram) {
        this.userTelegram = userTelegram;
    }    
}