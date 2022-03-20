package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.RandomUserDTO;
import com.project.dto.UserDTO;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @GetMapping("/status/check")
    public String status(){
        return "Working on port " + env.getProperty("local.server.port")
                + " with config environment = " + env.getProperty("token.check")
                + "\n" + "Test value: " + env.getProperty("token.test")
                + "\n" + "Ciphered value: " + env.getProperty("token.ciphered");
    }

    @GetMapping("/user")
    @PreAuthorize("#email == authentication.principal.username")
    public UserDTO getUser(@RequestParam String email, HttpServletRequest request) throws Exception {
        return userService.getUser(email, request);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO){
        userService.createUser(userDTO);
        return userDTO;
    }

    @PutMapping
    public UserDTO updateUser(@RequestParam String email, @Valid @RequestBody UserDTO userDTO){
        userService.updateUser(email, userDTO);
        return userDTO;
    }

    @DeleteMapping
    public String deleteUser(@RequestParam String email){
        userService.deleteUser(email);
        return "User has been successfully deleled!";
    }

    @GetMapping("/batch")
    public String getRoot() throws JsonProcessingException {
        userService.batchProcess();
        return "Batch has finished successfully!";
    }

}
