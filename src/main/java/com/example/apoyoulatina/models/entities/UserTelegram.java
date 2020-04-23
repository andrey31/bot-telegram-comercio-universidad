package com.example.apoyoulatina.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserTelegram
 */
@Entity
@Table(name = "users_telegram")
public class UserTelegram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long idTelegram;

    private int type;

    public UserTelegram() {
    }

    public UserTelegram(long id, long idTelegram, int type) {
        this.id = id;
        this.idTelegram = idTelegram;
        this.type = type;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdTelegram() {
        return this.idTelegram;
    }

    public void setIdTelegram(long idTelegram) {
        this.idTelegram = idTelegram;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }    

}