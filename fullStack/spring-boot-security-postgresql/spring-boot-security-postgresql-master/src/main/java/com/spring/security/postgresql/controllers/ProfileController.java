package com.spring.security.postgresql.controllers;

import com.spring.security.postgresql.models.User;
import com.spring.security.postgresql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class ProfileController {
@Autowired
UserRepository userRepository;



    @PostMapping("/profile")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void saveUser(@RequestBody User user) {
        try {
//            User newuser = this.userRepository.findById(user.getId()).get();
//            user.setRoles(newuser.getRoles());

            userRepository.save(user);
        }catch(Exception e){
            System.out.println("error input");
        }
    }

//    @PutMapping("/profile")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public void updateUser(@RequestBody User user) {
//        try {
//
//           //
//        }catch(Exception e){
//            System.out.println("error input");
//        }
//    }

//    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    public ResponseEntity<List<Stock>> getById(@RequestParam(required = false) String phone) {
//        try {
//            //Optional<Stock> stockData = stockRepository.findById(id);
//            List<Stock> tutorials = new ArrayList<Stock>();
////        if (stockData.isPresent()) {
////            return new ResponseEntity<>(stockData.get(), HttpStatus.OK);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
//            if (phone == null)
//                stockRepository.findAll().forEach(tutorials::add);
//            else
//                stockRepository.findByPhone(phone).forEach(tutorials::add);
//
//            if (tutorials.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(tutorials, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}



