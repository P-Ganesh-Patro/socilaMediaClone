package com.liquibase.demo.repository;

import com.liquibase.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM users u WHERE u.username = :username", nativeQuery = true)
    User findByUserName(@Param("username") String userName);


//    @Query(value = "SELECT u.username FROM users u WHERE u.username = :username", nativeQuery = true)
//    User findUserName(@Param("username") String userName);

    @Query(value = "SELECT u.email FROM users u WHERE u.email = :email", nativeQuery = true)
    User findByUserEmail(@Param("email") String email);

//    @Query(value = "SELECT * FROM users WHERE (username = :userNameOrEmail or email = :userNameOrEmail) AND password = :password", nativeQuery = true)
//    User findByUserNameOrEmailAndPassword(@Param("userNameOrEmail") String userNameOrEmail, @Param("password") String password);

    @Query(value = "SELECT * FROM users WHERE username = :userNameOrEmail OR email = :userNameOrEmail", nativeQuery = true)
    User findByUserNameOrEmail(@Param("userNameOrEmail") String userNameOrEmail);


}
