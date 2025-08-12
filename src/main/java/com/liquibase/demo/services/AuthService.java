package com.liquibase.demo.services;


import com.liquibase.demo.dto2.ChangePasswordRequestDTO;
import com.liquibase.demo.dto2.SignUpRequestDTO;
import com.liquibase.demo.dto2.SignUpResponseDTO;
import com.liquibase.demo.exception.Exception;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    WelcomeEmailMessage emailMessage;


    public SignUpResponseDTO createUser(SignUpRequestDTO user) {
        System.out.println(user);
        String username = user.getUserName();
        String email = user.getEmail();

        String existingUserName = authRepository.findByUserName(username);
        String existingEmail = authRepository.findByUserEmail(email);

        if (existingUserName != null && existingUserName.equalsIgnoreCase(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (existingEmail != null && existingEmail.equalsIgnoreCase(email)) {
            throw new RuntimeException("Email already exists");
        }


        User users = new User();
        users.setUsername(username);
        users.setEmail(email);
        users.setFirstName(user.getFirstName());
        users.setLastName(user.getLastName());
        users.setDateOfBirth(user.getDob());
        users.setPassword(passwordEncoder.encode(user.getPassword()));


        User saveUser = authRepository.save(users);

//        String emailBody =
//                "Hey, " + user.getFirstName() +
//                        """
//                                             "Just wanted to let you know I've created a new Instagram account, +
//                                            "Looking forward to connecting with you there!+
//                                            "Best"
//                                """;
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(user.getEmail());
//        simpleMailMessage.setSubject("New Instagram Account Created!");
//        simpleMailMessage.setText(emailBody);
//        javaMailSender.send(simpleMailMessage);

        emailMessage.sendWelcomeEmail(users);


        return new SignUpResponseDTO(
                saveUser.getId(),
                saveUser.getUsername(),
                saveUser.getFirstName(),
                saveUser.getLastName(),
                saveUser.getEmail(),
                saveUser.getCreatedAt(),
                saveUser.getDateOfBirth()

        );
    }

    public ResponseEntity<User> loginUser(String usernameOrEmail, String password) {

        User user = authRepository.findByUserNameOrEmail(usernameOrEmail);
        if (user == null) {
            throw new RuntimeException("Invalid usernameOrEmail");
        }
        if (user.getDeletedAt() != null) {
            throw new UserNotFoundException("user not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println(password + user.getPassword());
            throw new RuntimeException("Invalid username or password");
        }

        return ResponseEntity.ok(user);
    }

    public User getUser(Long id, ChangePasswordRequestDTO changePasswordRequestDTO) {

        User user = authRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(), user.getPassword())) {
            throw new Exception("Old password is not correct");
        }

        if (passwordEncoder.matches(changePasswordRequestDTO.getNewPassword(), user.getPassword())) {
            throw new Exception("Current password and new password must not be the same");
        }

        if (!changePasswordRequestDTO.getConfirmPassword().equals(changePasswordRequestDTO.getNewPassword())) {
            throw new Exception("New password and confirm password must be the same");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        return authRepository.save(user);
    }

}
