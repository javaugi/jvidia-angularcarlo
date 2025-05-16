/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jvidia.reactcloan.controller;

import com.jvidia.reactcloan.entity.Client;
import com.jvidia.reactcloan.repo.ClientRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author javaugi
 */
@RestController
public class WebMvcDefaultController {
  
    private final ClientRepository clientRepository;

    public WebMvcDefaultController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    List<Client> allClients;
    
    @PostConstruct
    public void init() {
        createClients();
        allClients = clientRepository.findAll();
        allClients.stream().forEach(System.out::print);
    }    

    @GetMapping
    public List<Client> getClients() {
        return clientRepository.findAll();
    }    
    
    private static final int n = 10;
    private List<Client> createClients() {
        List<Client> returnValue = new ArrayList<>();
        Client client;
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            client = new Client(CLIENT_NAMES.get(rand.nextInt(CLIENT_NAMES.size())), EMAILS.get(rand.nextInt(EMAILS.size())));
            returnValue.add(client);
        }

        clientRepository.saveAll(returnValue);
        return returnValue;
    }
    
    private static final List<String> CLIENT_NAMES = List.of("ABC Warehouse", "ABC Media", "CBS Media", "NBC Media", "ESPAN", 
            "SISLLC", "CBS Finance", "ABC Finance", "NBC Finance", "FOX Finance", "CNBC", "MS NBC");
    private static final List<String> EMAILS = List.of("jdoe@email.com", "asmith@email.com", "dbush@email.com", "lizChen@mail.com", 
        "aaa@ciminc.com", "a12@ciminc.com", "a2a@ciminc.com", "alibaba@ciminc.com", "alab@ciminc.com", "alex@ciminc.com",
        "arby@ciminc.com", "Allen@ciminc.com", "Abbey@ciminc.com", "Ashley@ciminc.com", "Ben@ciminc.com", "Bill@ciminc.com", 
        "Carol@ciminc.com", "Dan@ciminc.com", "Don@ciminc.com", "Doug@ciminc.com", "Ernie@ciminc.com", "Gary@ciminc.com",
        "Jax@ciminc.com", "Jon@ciminc.com", "Jeff@ciminc.com", "Jessica@ciminc.com", "Kevin@ciminc.com", "Shannon@ciminc.com");
}


/*
/*
Key Features

RESTful API with HATEOAS:
    Resources include links to related resources
    Follows REST principles
    Self-descriptive messages

Web Interface with Spring MVC:
    Traditional server-side rendering
    Thymeleaf templates for HTML generation
    Simple CRUD operations through web forms

Data Model:
    JPA entities with proper relationships
    Repository pattern for data access

Separation of Concerns:
    API endpoints separate from web interface
    Clear distinction between data model and resource representation

This implementation provides a solid foundation that can be extended with additional features like:

    Authentication and authorization
    Validation
    Advanced search capabilities
    Pagination
    Caching
    API documentation with Swagger
*/
