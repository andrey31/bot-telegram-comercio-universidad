package com.example.apoyoulatina.models.dao;

import com.example.apoyoulatina.models.entities.Question;

import org.springframework.data.repository.CrudRepository;

/**
 * QuestionDAO
 */
public interface QuestionDAO extends CrudRepository<Question, Integer>{

    
}