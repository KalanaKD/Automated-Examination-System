package com.auto.exam.service;

import com.auto.exam.Model.CourseRegister;
import com.auto.exam.Model.Exam;
import com.auto.exam.Model.ExamRequest;
import com.auto.exam.Model.Student;
import com.auto.exam.Model.User;
import com.auto.exam.Model.UserPrincipal;
import com.auto.exam.repo.courseRegisterRepo;
import com.auto.exam.repo.examRepo;
import com.auto.exam.repo.userRepo;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class examService {
    private examRepo examRepo;
    private courseRegisterRepo courseRegisterRepo;
    private userRepo userRepo;
    private studentDetailsService studentDetailsService;

    public examService(examRepo examRepo, courseRegisterRepo courseRegisterRepo, userRepo userRepo, studentDetailsService studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
        this.examRepo = examRepo;
        this.courseRegisterRepo = courseRegisterRepo;
        this.userRepo = userRepo;
    }
    

    public List<Exam> getExamsUsingDate(ExamRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();        

        Date date;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = formatter.parse(request.getDate());
        } catch (ParseException e) {
            return null;
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String user_name = userPrincipal.getUsername();
        List<Exam> exams = examRepo.findExamByUser(user_name);
        return exams.stream().filter(exam -> exam.getStartDateTime().toString().equals(outputFormat.format(date))).toList();

    }
}
