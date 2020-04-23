package com.example.apoyoulatina.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * QuestionType
 */
@Entity
@Table(name = "questions_type")
public class QuestionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String questions_type;

    public QuestionType() {
    }

    public QuestionType(int id, String questions_type) {
        this.id = id;
        this.questions_type = questions_type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestions_type() {
        return this.questions_type;
    }

    public void setQuestions_type(String questions_type) {
        this.questions_type = questions_type;
    }

}