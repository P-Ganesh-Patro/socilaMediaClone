//package com.liquibase.demo.repository;
//
//import com.liquibase.demo.model.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class AuthRepositoryTest {
//
//    @Autowired
//    AuthRepository authRepository;
//
//
//    @Test
//    public void testFindByUsername() {
//        User user = new User();
//        user.setUserName("ganesh");
//        user.setEmail("ganeshpatro@gmail.com");
//        user.setPassword("ganesh@1234");
//
//        authRepository.save(user);
//        String foundUsername = authRepository.findByUserName("ganesh");
//        Assertions.assertEquals("ganesh", foundUsername);
//
//    }
//
//}
