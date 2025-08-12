package com.liquibase.demo.controller;

import com.liquibase.demo.dto2.LoginDTO;
import com.liquibase.demo.dto2.SignUpDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.jwt.JwtUtil;
import com.liquibase.demo.model.User;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth/user")
@AllArgsConstructor
@Tag(name = "User Authentication", description = "Handles user signup and login operations")
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @Operation(summary = "user signUp")
    @PostMapping("/signup")
    public ResponseEntity<APIResponse<SignUpDTO>> signUpUser(@RequestBody User user) {
        try {

            LocalDate localDateTime = LocalDate.now();
            if ((user.getDateOfBirth().isAfter(localDateTime))) {
                throw new IllegalStateException("Date of birth must be before the current date");
            }
            User createdUser = authService.createUser(user);
            SignUpDTO dto = new SignUpDTO(
                    createdUser.getId(),
                    createdUser.getUsername(),
                    createdUser.getFirstName(),
                    createdUser.getLastName(),
                    createdUser.getEmail(),
                    createdUser.getDateOfBirth()
            );

            APIResponse<SignUpDTO> response = new APIResponse<>("User registered successfully",  dto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            APIResponse<SignUpDTO> response = new APIResponse<>(ex.getMessage(),  null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "user Login")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginDTO>> loginUser(@RequestBody Map<String, String> loginData) {
        try {
            String usernameOrEmail = loginData.get("username");
//            or email
            String password = loginData.get("password");
            User user = authService.loginUser(usernameOrEmail, password).getBody();

            LoginDTO loginDTO = new LoginDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getComments(),
                    user.getPosts(),
                    user.getReactions()
            );

            String accessToken = jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", loginDTO);
            responseData.put("accessToken", accessToken);
            responseData.put("refreshToke", refreshToken);

            APIResponse<Map<String, Object>> response = new APIResponse<>(
                    "Login successful",
                    responseData
            );
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new UserNotFoundException("Invalid username or password");
        } catch (Exception e) {
            throw new com.liquibase.demo.exception.Exception("Login failed:- " + e.getMessage());
        }
    }

}
