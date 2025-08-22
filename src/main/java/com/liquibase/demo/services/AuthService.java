package com.liquibase.demo.services;


import com.liquibase.demo.dto2.ChangePasswordRequestDTO;
import com.liquibase.demo.dto2.RefreshTokenResponseGenerateDTO;
import com.liquibase.demo.dto2.SignUpRequestDTO;
import com.liquibase.demo.dto2.SignUpResponseDTO;
import com.liquibase.demo.exception.Exception;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.jwt.JWTTokenValidation;
import com.liquibase.demo.jwt.JwtUtil;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.AuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    WelcomeEmailMessage emailMessage;


    @Autowired
    JwtUtil jwtUtil;

    public SignUpResponseDTO createUser(SignUpRequestDTO user) {

        String username = user.getUserName();
        String email = user.getEmail();

        User existingUserName = authRepository.findByUserName(username);
        User existingEmail = authRepository.findByUserEmail(email);


        if (existingUserName != null && existingUserName.getUsername().equalsIgnoreCase(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (existingEmail != null && existingEmail.getEmail().equalsIgnoreCase(email)) {
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

    public User changePassword(ChangePasswordRequestDTO changePasswordRequestDTO, String username) {

        User user = authRepository.findByUserName(username);

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


    public RefreshTokenResponseGenerateDTO generateRefreshTokenRefresh(String refreshToken) {
        if (refreshToken == null) {
            throw new Exception("token is null");
        }

        if (jwtUtil.validateTheTokens(refreshToken).equals(JWTTokenValidation.VALID)) {
            String username = jwtUtil.extractUsername(refreshToken);
            String newAccessToken = jwtUtil.generateAccessToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);
            return new RefreshTokenResponseGenerateDTO(
                    newAccessToken,
                    newRefreshToken
            );
        }
        return null;

    }
}
