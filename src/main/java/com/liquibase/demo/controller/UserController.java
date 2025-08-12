
package com.liquibase.demo.controller;


import com.liquibase.demo.dto2.SignUpResponseDTO;
import com.liquibase.demo.dto2.UserUpdateRequestDTO;
import com.liquibase.demo.dto2.UserUpdateResponseDTO;
import com.liquibase.demo.exception.UserNotFoundException;
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
    public ResponseEntity<APIResponse<UserUpdateResponseDTO>> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO user) {
        try {

            UserUpdateResponseDTO updatedUser = userServiceImpl.updateUser(id, user);

            APIResponse<UserUpdateResponseDTO> response = new APIResponse<>("User updated successfully", updatedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            APIResponse<UserUpdateResponseDTO> response = new APIResponse<>("User update failed: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            APIResponse<UserUpdateResponseDTO> response = new APIResponse<>("Unexpected error: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "delete user by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<APIResponse<SignUpResponseDTO>> deleteUser(@PathVariable Long id) {
        try {
            SignUpResponseDTO deletedUser = userServiceImpl.deleteUser(id);

            APIResponse<SignUpResponseDTO> response = new APIResponse<>("User deleted successfully", deletedUser);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e) {
            APIResponse<SignUpResponseDTO> response = new APIResponse<>("User deletion failed: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            APIResponse<SignUpResponseDTO> response = new APIResponse<>("Unexpected error: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<SignUpResponseDTO>> userGetById(@PathVariable Long id) {
        try {
            SignUpResponseDTO user = userServiceImpl.getUserById(id);

            APIResponse<SignUpResponseDTO> response = new APIResponse<>(
                    "user fetched successfully",
                    user
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }

    }


}
