/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jvidia.reactcloan.controller;

import com.jvidia.reactcloan.entity.User;
import com.jvidia.reactcloan.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;

@Configuration
@Path("/jersey")
public class UserRestJerseyController {
    private static final Logger log = LoggerFactory.getLogger(UserAngularController.class);

    // standard constructors
    @Autowired
    private UserRepository userRepository;
    
    @GET
    @Path("/data")
    public List<String> getData() {
        return Arrays.asList("Item 1", "Item 2", "Item 3");
    }    

    @GET
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @POST
    public void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
    
    @PostConstruct
    public void init() {
        createUsers();
    }
    
    private void createUsers() {
        log.info("UserRestJerseyController ..."); 
        Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
            User user = new User(name, name.toLowerCase() + "@domain.com");
            userRepository.save(user);
        });
        userRepository.findAll().forEach(System.out::println);
    }
    
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        log.info("UserRestJerseyController CommandLineRunner init {}", userRepository); 
        return args -> {
            Stream.of("Adam", "Jane", "Jonathan", "Allen", "Ricky").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
        };
    }
    
        
}
