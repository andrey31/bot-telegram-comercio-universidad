package com.example.apoyoulatina.models.dao;

import java.util.List;

import com.example.apoyoulatina.models.entities.CourseByStudent;

import org.springframework.data.repository.CrudRepository;

/**
 * CourseByStudentDAO
 */
public interface CourseByStudentDAO extends CrudRepository<CourseByStudent, Integer>{

    public List<CourseByStudent> findByUserUserTelegramIdTelegram(long idTelegram);
    public List<CourseByStudent> findByUserUserTelegramIdTelegramAndStatus(long idTelegram, byte status);
    public CourseByStudent findByUserUserTelegramIdTelegramAndStatusAndId(long idTelegram, byte status, int id);
}