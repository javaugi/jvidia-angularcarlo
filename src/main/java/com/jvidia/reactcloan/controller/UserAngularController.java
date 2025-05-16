 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jvidia.reactcloan.controller;

import static com.jvidia.reactcloan.controller.UserController.getAlphaNumericString;
import static com.jvidia.reactcloan.controller.UserController.getTwoDigitString;
import com.jvidia.reactcloan.entity.User;
import com.jvidia.reactcloan.repo.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wfausers")
//http://localhost:8080/users
@CrossOrigin(origins = "http://localhost:4200")
@org.springframework.core.annotation.Order(9)
public class UserAngularController {
    private static final Logger log = LoggerFactory.getLogger(UserAngularController.class);

    // standard constructors
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/data")
    public List<String> getData() {
        return Arrays.asList("Item 1", "Item 2", "Item 3");
    }    

    @GetMapping
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
    
    private int totalCount = 20;
    public void run(String... args) throws Exception {
        log.info("UserAngularController with args {}", Arrays.toString(args)); 
        totalCount = 20;
        createUsers();
        log.info("UserAngularController Users created ..."); 
        userRepository.findAll().forEach(System.out::println);
        log.info("Done UserAngularController run setup"); 
    }
    
    private List<User> createUsers() {

        List<User> returnValue = new ArrayList();
        User user = null;
        Random rand = new Random();
        for (int i = 0; i < totalCount; i++) {
            user = new User();
            user.setFirstName(FIRST_NAMES.get(rand.nextInt(FIRST_NAMES.size())));
            user.setLastName(LAST_NAMES.get(rand.nextInt(LAST_NAMES.size())));
            user.setUsername(user.getFirstName().substring(0, 1).toLowerCase() + user.getLastName().toLowerCase() + getTwoDigitString(2));
            user.setUserEmail(getAlphaNumericString(5) + LAST_NAMES.get(rand.nextInt(LAST_NAMES.size())));

            userRepository.save(user);
            log.info("createUsers i user created "); 
            returnValue.add(user);
        }

        //userPagingRepositary.saveAll(returnValue);
        //userCrudRepo.saveAll(returnValue);
        return returnValue;
    }
    
    private static final List<String> FIRST_NAMES = List.of("Alex", "Arby", "Allen", "Abbey", "Ashley", "Ben", "Bill", "Carol", 
            "Dan", "Don", "Doug", "Ernie", "Gary", "Jax", "Jon", "Jeff", "Jessica", "Kevin", "Shannon");
    private static final List<String> LAST_NAMES = List.of("Alton", "Aleon", "Atux", "Lee", "Swift", "Liu", "Alexon", "Alatian",
            "Smith", "Smita", "Will", "Wall", "Zina"); 
}