package com.example.miexpense.services.ServiceImplementation;

import com.example.miexpense.model.User;
import com.example.miexpense.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(10L);
        user.setFirstname("King");
        user.setLastname("Kong");
        user.setEmail("king@gmail.com");
        user.setPassword("1234");
        user.setDob("12-04-1990");
        user.setGender("Male");
    }

    @Test
    void shouldTestForCreationOfUser() {
        //mock UserRepo

        when(userRepo.save(any(User.class))).thenReturn(user);

        //call method to be tested

        userService.createUser(user);

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void shouldTestForGettingUser() {
        //mock user Repo

        when(userRepo.findPersonByEmail(user.getEmail())).thenReturn(user);

        // call method to be tested

        User userDetail = userService.getUser(user.getEmail(), "1234");

        //assertions
        assertNotNull(userDetail);
        assertEquals("king@gmail.com", userDetail.getEmail());
        assertEquals("King", userDetail.getFirstname());
        assertEquals("Kong", userDetail.getLastname());
        assertEquals("Male", userDetail.getGender());
        assertEquals("1234", userDetail.getPassword());

        verify(userRepo, times(1)).findPersonByEmail(user.getEmail());

    }

//    @Test
//    void shouldTestForDeletingOfUser() {
//        //mock User Repo
//
//        when(userRepo.deleteUserByEmail(user.getEmail())).thenReturn(user);
//
//        // call method to be tested
//
//        User userDetail = userService.deleteUser(user.getEmail());
//
//        //assertions
//        assertEquals("king@gmail.com", userDetail.getEmail());
//        assertEquals("King", userDetail.getFirstname());
//        assertEquals("Kong", userDetail.getLastname());
//        assertEquals("Male", userDetail.getGender());
//        assertEquals("1234", userDetail.getPassword());
//
//    }
}