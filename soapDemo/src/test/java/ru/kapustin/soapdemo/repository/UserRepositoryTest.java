package ru.kapustin.soapdemo.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kapustin.soapdemo.entity.User;
import ru.kapustin.soapdemo.service.UserService;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @InjectMocks
    UserService userService;


    @Test
    void shouldAddUser() {
        User user2 = new User();

        user2.setUserId(15L);
        user2.setName("checkName");
        user2.setSurname("checkSurname");
        user2.setPhone("89202546635");
        user2.setEmail("check@mail.ru");

        User savedUser = userRepository.save(user2);

        Assertions.assertTrue(userRepository.findAll().contains(user2));

    }


    @Test
    void shouldFindUserById() {
        User user2 = new User();

        user2.setUserId(3L);
        user2.setName("checkName");
        user2.setSurname("checkSurname");
        user2.setPhone("89202546635");
        user2.setEmail("check@mail.ru");

        userRepository.save(user2);

    }


    @Test
    void shouldFindAllUser() {
        User user = User.builder().
                userId(1L)
                .name("checkName")
                .surname("checkSurname")
                .phone("89204140919")
                .email("tset@mail.ru")
                .build();

        userRepository.save(user);

        User user1 = User.builder().
                userId(2L)
                .name("checkName")
                .surname("checkSurname")
                .phone("89204140919")
                .email("tset@mail.ru")
                .build();

        userRepository.save(user);
        userRepository.save(user1);

        List<User> userList = userRepository.findAll();
        org.assertj.core.api.Assertions.assertThat(userList).isNotNull();
        org.assertj.core.api.Assertions.assertThat(userList.size()).isEqualTo(userRepository.findAll().size());
    }

    @Test
    void shouldUpdateUser() {
        User user = User.builder()
                .userId(1L)
                .name("testName")
                .surname("testSurname")
                .phone("89204140919")
                .email("test@mail.ru")
                .build();

        userRepository.save(user);

        Assertions.assertEquals("testSurname", user.getSurname());
        user.setSurname("teset");

        userRepository.save(user);

        Assertions.assertEquals("teset", user.getSurname());


    }

    @Test
    void shouldDeleteUser() {
        User user = User.builder()
                .userId(1L)
                .name("testName")
                .surname("testSurname")
                .phone("89204140919")
                .email("test@mail.ru")
                .build();

        userRepository.save(user);

        userRepository.deleteById(user.getUserId());

        Optional<User> userReturn = userRepository.findById(user.getUserId());

        org.assertj.core.api.Assertions.assertThat(userReturn).isEmpty();
    }

    @Test
    void shouldFindUsersByNameAndSurnameAndPhoneAndEmail() {
        User user = User.builder()
                .userId(1L)
                .name("testName")
                .surname("testSurname")
                .phone("89204140919")
                .email("test@mail.ru")
                .build();

        userRepository.save(user);

        List<User> userList = userRepository.findUsersByNameAndSurnameAndPhoneAndEmail(user.getName(), user.getSurname(), user.getPhone(), user.getEmail());

        org.assertj.core.api.Assertions.assertThat(userList).isNotNull();
    }
}

