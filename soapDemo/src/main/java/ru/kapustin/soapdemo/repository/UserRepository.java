package ru.kapustin.soapdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kapustin.soapdemo.entity.User;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserId(Long userId);

    public List<User> findUsersByNameAndSurnameAndPhoneAndEmail(String name, String surname, String phone, String email);
}
