package ru.kapustin.soapdemo.service;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kapustin.soapdemo.entity.User;
import ru.kapustin.soapdemo.repository.UserRepository;
import ru.kapustin.user_ws.UserInfo;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService implements IUserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    final String patternPhone = "^\\+?8(9\\d{9})$";
    final String patternEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    public List<User> getAllUser() {
        logger.debug("Find all users.");
        List<User> userList = userRepository.findAll();
        return userList;
    }

    @Override
    public User getUserById(Long userId) throws EntityNotFoundException {
        logger.debug("Find user by id = {}", userId);
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Can't find user with id = " + userId));
    }

    @Override
    public boolean addUser(User user) {

        List<User> userList = userRepository.findUsersByNameAndSurnameAndPhoneAndEmail(user.getName(), user.getSurname(), user.getPhone(), user.getEmail());
        boolean phoneFlag = user.getPhone().matches(patternPhone);
        boolean emailFlag = user.getEmail().matches(patternEmail);
        if (userList.size() > 0) {
            logger.debug("Can't save user.");
            return false;
        } else {
            if ((user.getName().isBlank() || user.getSurname().isBlank() || user.getPhone().isBlank() || user.getEmail().isBlank())
                    ||
                    (user.getName().equals("?") || user.getSurname().equals("?") || user.getPhone().equals("?") || user.getEmail().equals("?"))
                    ||
                    (phoneFlag == false || emailFlag == false)) {
                logger.debug("Can't save user.");
                return false;
            } else {
                userRepository.save(user);
                logger.debug("Save user.");
                return true;
            }
        }
    }

    @Override
    public boolean updateUser(User user) {
        boolean phoneFlag = user.getPhone().matches(patternPhone);
        boolean emailFlag = user.getEmail().matches(patternEmail);
        if (user.getName().equals("?") || user.getSurname().equals("?") || user.getPhone().equals("?") || user.getEmail().equals("?") || emailFlag == false || phoneFlag == false) {
            logger.debug("Can't update user.");
            return false;
        } else {
            logger.debug("Update user.");
            userRepository.save(user);
            return true;
        }

    }

    @Override
    public void deleteUser(User user) {
        if (user.getUserId() != null) {
            logger.debug("Delete user.");
            userRepository.delete(user);
        }
    }
}
