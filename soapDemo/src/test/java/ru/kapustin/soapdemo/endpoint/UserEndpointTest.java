package ru.kapustin.soapdemo.endpoint;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kapustin.soapdemo.entity.User;
import ru.kapustin.soapdemo.service.UserService;
import ru.kapustin.user_ws.GetAllUsersResponse;
import ru.kapustin.user_ws.GetUserByIdRequest;
import ru.kapustin.user_ws.GetUserByIdResponse;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserEndpointTest {

    private final String URI = "http://localhost:8080/soapws/users.wsdl";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserEndpoint userEndpoint;


    @Test
    void shouldGetAllUsers() {
        User user2 = new User();
        user2.setUserId(3L);
        user2.setName("checkName");
        user2.setSurname("checkSurname");
        user2.setPhone("89202546635");
        user2.setEmail("check@mail.ru");
        List<User> expectedUser = Arrays.asList(user2);
        when(userService.getAllUser()).thenReturn(expectedUser);
        GetAllUsersResponse response = userEndpoint.getAllUsers();
        assertNotNull(response.getUserInfo().isEmpty());
        Assertions.assertEquals(expectedUser.size(), response.getUserInfo().size());
    }

    @Test
    void shouldGetUserById() {
        GetUserByIdRequest userId = new GetUserByIdRequest();
        userId.setUserId(3L);
        User expectedUser = new User();
        GetUserByIdRequest request = new GetUserByIdRequest();
        expectedUser.setUserId(3L);
        expectedUser.setName("checkName");
        expectedUser.setSurname("checkSurname");
        expectedUser.setPhone("89202546635");
        expectedUser.setEmail("check@mail.ru");

        when(userService.getUserById(3L)).thenReturn(expectedUser);

        GetUserByIdResponse response = userEndpoint.getUser(userId);

        assertNotNull(response);
        assertEquals(expectedUser.getUserId(), response.getUserInfo().getUserId());
        assertEquals(expectedUser.getName(), response.getUserInfo().getName());
    }

}
