package ru.kapustin.soapdemo.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kapustin.soapdemo.entity.User;
import ru.kapustin.soapdemo.service.UserService;
import ru.kapustin.user_ws.*;

import java.util.ArrayList;
import java.util.List;

import static ru.kapustin.soapdemo.enumstatus.StatusCodeEnum.*;

@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://www.kapustin.ru/user-ws";

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserEndpoint.class);

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUser(@RequestPayload GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        User user = userService.getUserById(request.getUserId());
        UserInfo userInfo = new UserInfo();
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null) {
            logger.info("Can't find user");
            serviceStatus.setStatusCode(String.valueOf(FAIL));
            serviceStatus.setMessage("Content Not Available");
            response.setServiceStatus(serviceStatus);
        } else {
            logger.info("Find user with id = {}.", user.getUserId());
            BeanUtils.copyProperties(userService.getUserById(request.getUserId()), userInfo);
            response.setServiceStatus(serviceStatus);
            response.setUserInfo(userInfo);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<UserInfo> userInfoList = new ArrayList<>();
        List<User> userList = userService.getAllUser();
        for (User user : userList) {
            UserInfo objct = new UserInfo();
            BeanUtils.copyProperties(user, objct);
            userInfoList.add(objct);
        }
        logger.info("Return list of users.");
        response.getUserInfo().addAll(userInfoList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        boolean flag = userService.addUser(user);
        if (flag == false) {
            logger.info("Can't add user");
            serviceStatus.setStatusCode(String.valueOf(CONFLICT));
            serviceStatus.setMessage("Content Already Available or incorrect data\n" +
                    "email format: test@mail.ru\n" +
                    "phone format: 8xxxxxxxxxx");
            response.setServiceStatus(serviceStatus);
        } else {
            logger.info("Add user");
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(user, userInfo);
            response.setUserInfo(userInfo);
            serviceStatus.setStatusCode(String.valueOf(SUCCESS));
            serviceStatus.setMessage("Content Added Successfully");
            response.setServiceStatus(serviceStatus);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        User user = userService.getUserById(request.getUserInfo().getUserId());
        User userUpdate = new User();
        UpdateUserResponse response = new UpdateUserResponse();
        BeanUtils.copyProperties(request.getUserInfo(), userUpdate);
        ServiceStatus serviceStatus = new ServiceStatus();
        boolean flag = userService.updateUser(userUpdate);
        if (flag == false) {
            logger.info("Can't update user.");
            serviceStatus.setStatusCode(String.valueOf(FAIL));
            serviceStatus.setMessage("Content Not Available\n" +
                    "email format: test@mail.ru\n" +
                    "phone format: 8xxxxxxxxxx");
        } else {
            logger.info("Update user with id = {}.", user.getUserId());
            userService.updateUser(userUpdate);
            serviceStatus.setStatusCode(String.valueOf(SUCCESS));
            serviceStatus.setMessage("Content Updated Successfully");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        User user = userService.getUserById(request.getUserId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null) {
            logger.info("Can't delete user.");
            serviceStatus.setStatusCode(String.valueOf(FAIL));
            serviceStatus.setMessage("Content Not Available");
        } else {
            logger.info("Delete user with id = {}.", user.getUserId());
            userService.deleteUser(user);
            serviceStatus.setStatusCode(String.valueOf(SUCCESS));
            serviceStatus.setMessage("Content Deleted Successfully");
        }
        DeleteUserResponse response = new DeleteUserResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
}
