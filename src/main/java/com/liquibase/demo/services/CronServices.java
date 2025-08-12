package com.liquibase.demo.services;

import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CronServices {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JavaMailSender javaMailSender;


    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Kolkata")
//    @Scheduled(cron = "0 44 11 * * *", zone = "Asia/Kolkata")
    public void sendBirthWish() {
        LocalDate todayDate = LocalDate.now();
        List<User> birthdayUsers = userRepository.findAll().stream().filter(user -> {
            LocalDate dob = user.getDateOfBirth();
            return dob != null && dob.getMonth() == todayDate.getMonth() && dob.getDayOfMonth() == todayDate.getDayOfMonth();
        }).toList();

        for (User wishUser : birthdayUsers) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(wishUser.getEmail());
            simpleMailMessage.setText("Wish you a Happy Birth day 🎂 " + wishUser.getFirstName());
            simpleMailMessage.setSubject("Happy Birthday " + wishUser.getFirstName());
            javaMailSender.send(simpleMailMessage);
        }
        System.out.println("birthday wish sent");

    }
}
