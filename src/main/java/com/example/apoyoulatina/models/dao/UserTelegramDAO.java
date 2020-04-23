package com.example.apoyoulatina.models.dao;

import com.example.apoyoulatina.models.entities.UserTelegram;

import org.springframework.data.repository.CrudRepository;

/**
 * UserTelegramDAO
 */
public interface UserTelegramDAO extends CrudRepository<UserTelegram, Long>{
    public UserTelegram findByIdTelegram(long idTelegram);
}