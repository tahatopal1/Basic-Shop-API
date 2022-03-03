package com.project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.RandomUserDTO;
import com.project.dto.UserDTO;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public UserDTO getUser(@RequestParam String email){
        return userService.getUser(email);
    }

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser( @Valid @RequestBody UserDTO userDTO){
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
