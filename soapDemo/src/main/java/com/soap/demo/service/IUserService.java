package com.soap.demo.service;

import com.soap.demo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IUserService {
    List<User> getAllUser();
    User getUserById(Long userId);
    boolean addUser(User user);
    boolean updateUser(User user);
    void deleteUser(User user);
}
