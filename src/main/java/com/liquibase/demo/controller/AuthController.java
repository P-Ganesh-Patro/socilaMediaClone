package com.liquibase.demo.controller;

import com.liquibase.demo.dto2.*;
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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<APIResponse<SignUpResponseDTO>> signUpUser(@RequestBody SignUpRequestDTO user) {
        try {

            LocalDate today = LocalDate.now();

            if (user.getDob() == null) {
                throw new IllegalStateException("Date of birth cannot be null");
            }

            if (user.getDob().isAfter(today)) {
                throw new IllegalStateException("Date of birth must be before the current date");
            }

            SignUpResponseDTO createdUser = authService.createUser(user);


            APIResponse<SignUpResponseDTO> response = new APIResponse<>("User registered successfully", createdUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception ex) {
            APIResponse<SignUpResponseDTO> response = new APIResponse<>(ex.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "user Login")
    @PostMapping("/login")
    public ResponseEntity<APIResponse<LoginDTO>> loginUser(@RequestBody LoginRequestDTO loginData) {
        try {
            String usernameOrEmail = loginData.getUserNameOrEmail();
            String password = loginData.getPassword();
            User user = authService.loginUser(usernameOrEmail, password).getBody();

            LoginDTO loginDTO = new LoginDTO(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getComments(), user.getPosts(), user.getReactions());

            String accessToken = jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", loginDTO);
            responseData.put("accessToken", accessToken);
            responseData.put("refreshToke", refreshToken);

            APIResponse<Map<String, Object>> response = new APIResponse<>("Login successful", responseData);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new com.liquibase.demo.exception.Exception("Login failed:- " + e.getMessage());
        }
    }


    @Operation(summary = "User password change")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ChangePasswordResponseDTO>> passwordChange(@PathVariable Long id, @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) throws Exception {
        User user = authService.getUser(id, changePasswordRequestDTO);

        ChangePasswordResponseDTO responseDTO = new ChangePasswordResponseDTO(user.getId(), "Password changed successfully");

        APIResponse apiResponse = new APIResponse("password changed...", responseDTO);
        return new ResponseEntity(apiResponse, HttpStatus.OK);

    }

}
