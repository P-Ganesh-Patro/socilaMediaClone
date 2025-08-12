package com.liquibase.demo.services;

import com.liquibase.demo.dto2.SignUpResponseDTO;
import com.liquibase.demo.dto2.UserUpdateRequestDTO;
import com.liquibase.demo.dto2.UserUpdateResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public SignUpResponseDTO deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User ID not found");
        }
        User user = optionalUser.get();
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return new SignUpResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDateOfBirth()

        );

    }

    public UserUpdateResponseDTO updateUser(Long id, UserUpdateRequestDTO user) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        User existingUser = existingUserOptional.get();
        existingUser.setUsername(user.getUserName());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUpdatedAt(LocalDateTime.now());

        User updateUser = userRepository.save(existingUser);

        return new UserUpdateResponseDTO(
                updateUser.getId(),
                updateUser.getUsername(),
                updateUser.getFirstName(),
                updateUser.getLastName(),
                updateUser.getEmail(),
                updateUser.getDateOfBirth(),
                updateUser.getCreatedAt(),
                updateUser.getUpdatedAt()
        );

    }


    public SignUpResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).get();
        return new SignUpResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDateOfBirth()
        );


    }
}
