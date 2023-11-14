package ru.kapustin.soapdemo.service;

import org.springframework.stereotype.Service;
import ru.kapustin.soapdemo.entity.User;
import ru.kapustin.user_ws.UserInfo;

import java.util.List;

@Service
public interface IUserService {
    List<User> getAllUser();

    User getUserById(Long userId);

    boolean addUser(User user);

    boolean updateUser(User user);

    void deleteUser(User user);
}
