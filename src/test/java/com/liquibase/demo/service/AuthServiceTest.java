//package com.liquibase.demo.service;
//
//import com.liquibase.demo.model.User;
//import com.liquibase.demo.repository.AuthRepository;
//import com.liquibase.demo.service.authService.AuthServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthServiceTest {
//
//    @Mock
//    AuthServiceImpl authService;
//
//    @InjectMocks
//    AuthRepository authRepository;
//
//    @Test
//    public void createUserTest() {
//        User user = new User();
//        user.setUserName("ganesh");
//        user.setEmail("pganeshpatro@gmail.com");
//
//        Mockito.when(authRepository.findByUserEmail("pganeshpatro@gmail.com")).thenReturn(null);
//        Mockito.when(authRepository.findByUserName("ganesh")).thenReturn(null);
//
//        Mockito.when(authRepository.save(user)).thenReturn(user);
//
//        User created = authService.createUser(user);
//
//        Assertions.assertEquals("ganesh", created.getUserName());
//        Mockito.verify(authRepository, Mockito.times(1)).save(user);
//
//    }
//
//    @Test
//    void testCreateUserUsernameExists() {
//        User user = new User();
//        user.setUserName("ganesh");
//        user.setEmail("ganesh@example.com");
//
//        Mockito.when(authRepository.findByUserName("ganesh")).thenReturn("ganesh");
//
//        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
//            authService.createUser(user);
//        });
//
//        Assertions.assertEquals("Username already exists", exception.getMessage());
//    }
//}
