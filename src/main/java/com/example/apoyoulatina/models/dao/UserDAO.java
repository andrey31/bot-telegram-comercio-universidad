package com.example.apoyoulatina.models.dao;

import com.example.apoyoulatina.models.entities.User;

import org.springframework.data.repository.CrudRepository;

/**
 * UserDAO
 */
public interface UserDAO extends CrudRepository<User, Long>{

    public User findByUserTelegramIdTelegram(long id);

    public User findByIdentification(String identification);
}