package com.auto.exam.repo;

import com.auto.exam.Model.Exam;
import com.auto.exam.Model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface examRepo extends JpaRepository<Exam, Integer> {
    @Query("SELECT ex FROM Exam ex JOIN ex.courseOffering co JOIN co.courseRegisters cr JOIN cr.registration r JOIN r.student s JOIN s.user u WHERE u.username = :user_name")
    List<Exam> findExamByUser(@Param("user_name") String user_name);
}