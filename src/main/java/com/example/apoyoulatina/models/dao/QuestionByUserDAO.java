package com.example.apoyoulatina.models.dao;

import com.example.apoyoulatina.models.entities.QuestionByUser;

import org.springframework.data.repository.CrudRepository;

/**
 * QuestionByUserDAO
 */
public interface QuestionByUserDAO extends CrudRepository<QuestionByUser, Integer>{

    public QuestionByUser findByUserTelegramIdTelegramAndActive(long id, boolean active);
    
}