package com.example.apoyoulatina.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Answer
 */
@Entity
@Table(name = "answers")
public class Answer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String answer;

    @JoinColumn(name = "question_id")
    @OneToOne
    private Question question;
    
    @JoinColumn(name = "user_telegram_id")
    @OneToOne
    private UserTelegram userTelegram;

    private boolean active;


    public Answer() {
    }

    public Answer(int id, String answer, Question question, UserTelegram userTelegram, boolean active) {
        this.id = id;
        this.answer = answer;
        this.question = question;
        this.userTelegram = userTelegram;
        this.active = active;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}