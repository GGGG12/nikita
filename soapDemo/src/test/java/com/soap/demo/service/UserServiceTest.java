package com.soap.demo.service;


import com.soap.demo.entity.User;
import com.soap.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldReturnAllUsers() {
        User user1 = new User();

        user1.setUserId(1L);
        user1.setName("testName");
        user1.setSurname("testSurname");
        user1.setPhone("89204140919");
        user1.setEmail("test@test.ru");

        User user2 = new User();
        user1.setUserId(2L);
        user1.setName("checkName");
        user2.setSurname("checkSurname");
        user2.setPhone("89202546635");
        user2.setEmail("check@mail.ru");

        List<User> expected = List.of(user1,user2);
        when(userRepository.findAll()).thenReturn(expected);
        List<User> actual = userService.getAllUser();

        assertThat(actual).isNotNull();
        assertEquals(expected,actual);
        userService.getAllUser();
    }

    @Test
    void shouldAddUser(){
        User user = getUser();
        userService.addUser(user);
        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteUser(){
        User user = getUser();
        userService.deleteUser(user);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldUpdateUser(){
        User user = getUser();
        userService.updateUser(user);
        verify(userRepository).save(user);
    }

    @Test()
    void shouldNotReturnUserById() {
        when(userRepository.findById(1337L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1337L));
        assertEquals("Can't find user with id = 1337", exception.getMessage());
    }
    @Test
    void shouldReturnUserById(){
        Optional<User> expected = Optional.of(new User(3L, "testName", "testSurname", "89204140919","test@mail.ru"));
        when(userRepository.findById(1337L)).thenReturn(expected);

        User actual = userService.getUserById(1337L);
        assertEquals(expected.get(), actual);

        verify(userRepository, atLeastOnce()).findById(1337L);
    }

    private User getUser(){
        User user = new User();

        user.setUserId(1L);
        user.setName("testName");
        user.setSurname("testSurname");
        user.setPhone("89204140919");
        user.setEmail("test@test.ru");

        return user;
    }


}
