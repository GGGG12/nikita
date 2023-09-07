package com.spring.security.postgresql.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.spring.security.postgresql.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);



  //List<User> findByRoles(Collection<String> names, Pageable pageable);
  //@Query(nativeQuery = true,value = "SELECT username FROM users JOIN user_roles ON user_roles.user_id = users.id WHERE user_roles.role_id = 1")
  //@Query(nativeQuery = true,value = "SELECT username FROM users,user_roles WHERE id = user_id AND role_id = 1")
  //@Query(nativeQuery = true,value = "SELECT username FROM users,user_roles WHERE id = user_id AND role_id = 1")








}
