package com.example.apoyoulatina.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * QuestionByUser
 */
@Entity
@Table(name = "questions_by_user")
public class QuestionByUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean active;

    @OneToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToOne
    @JoinColumn(name = "user_telegram_id")
    private UserTelegram userTelegram;


    public QuestionByUser() {
    }

    public QuestionByUser(int id, boolean active, Question question, UserTelegram userTelegram) {
        this.id = id;
        this.active = active;
        this.question = question;
        this.userTelegram = userTelegram;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public UserTelegram getUserTelegram() {
        return this.userTelegram;
    }

    public void setUserTelegram(UserTelegram userTelegram) {
        this.userTelegram = userTelegram;
    }
    
}