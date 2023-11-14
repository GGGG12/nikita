package com.soap.demo.repository;

import com.soap.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);
    List<User> findUsersByNameAndSurnameAndPhoneAndEmail(String name,String surname,String phone, String email);
}
