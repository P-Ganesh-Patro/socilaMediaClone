
package com.liquibase.demo.controller;


import com.liquibase.demo.dto2.SignUpDTO;
import com.liquibase.demo.exception.UserNotFoundException;
import com.liquibase.demo.model.User;
import com.liquibase.demo.response.APIResponse;
import com.liquibase.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Tag(name = "User APIs", description = "update, delete & fetch- users")
public class UserController {
    private UserService userServiceImpl;

    @Operation(summary = "update user by id")
    @PutMapping("/update/{id}")
    public ResponseEntity<APIResponse<SignUpDTO>> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userServiceImpl.updateUser(user);

            SignUpDTO dto = new SignUpDTO(
                    updatedUser.getId(),
                    updatedUser.getUsername(),
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getEmail(),
                    updatedUser.getDateOfBirth()
            );

            APIResponse<SignUpDTO> response = new APIResponse<>("User updated successfully", dto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("User update failed: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("Unexpected error: " + e.getMessage(),  null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "delete user by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<SignUpDTO>> deleteUser(@PathVariable Long id) {
        try {
            User deletedUser = userServiceImpl.deleteUser(id);

            SignUpDTO dto = new SignUpDTO(
                    deletedUser.getId(),
                    deletedUser.getUsername(),
                    deletedUser.getFirstName(),
                    deletedUser.getLastName(),
                    deletedUser.getEmail(),
                    deletedUser.getDateOfBirth()

            );

            APIResponse<SignUpDTO> response = new APIResponse<>("User deleted successfully",  dto);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("User deletion failed: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            APIResponse<SignUpDTO> response = new APIResponse<>("Unexpected error: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SignUpDTO>> userGetById(@PathVariable Long id) {
        try {
            User user = userServiceImpl.getUserById(id);
            SignUpDTO signUpDTO = new SignUpDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getDateOfBirth()
            );
            APIResponse<SignUpDTO> response = new APIResponse<>(
                    "user fetched successfully",
                    signUpDTO
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }

    }


}
