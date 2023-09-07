package com.spring.security.postgresql.controllers;

import com.spring.security.postgresql.models.ERole;
import com.spring.security.postgresql.models.Role;
import com.spring.security.postgresql.models.User;
import com.spring.security.postgresql.repository.RoleRepository;
import com.spring.security.postgresql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
   private RoleRepository roleRepository;


    @GetMapping("/getUsers")
    public List <User> getUsers()
    {
        List<User> userList= userRepository.findAll();
        return userList;

    }

    @GetMapping("/getUsers/{id}")
    public User getUser(@PathVariable("id") long id)
    {
        return userRepository.findById(id).get();
    }



    @GetMapping("/users")
    public ResponseEntity <List<User>> getAllUsers()
    {
       List<Role>roleList =this.roleRepository.getRoleNotAdmin(List.of(ERole.ROLE_ADMIN,ERole.ROLE_MODERATOR));
       List<User> userList = roleList.stream()
               .flatMap(role -> role.getUserSet().stream())
               .distinct()
               .collect(Collectors.toList());
       userList.forEach(user ->user.setRoles(null)
               );


       return ResponseEntity.ok(userList);
    }


@DeleteMapping("/getUsers/{id}")
public ResponseEntity<HttpStatus> deleteUsers(@PathVariable("id") long id) {
    try {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
