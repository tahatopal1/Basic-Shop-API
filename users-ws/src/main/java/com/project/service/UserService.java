package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDTO> getAllUsers();

    UserDTO getUser(String email, HttpServletRequest request) throws Exception;

    void createUser(UserDTO userDTO);

    void updateUser(String email, UserDTO userDTO);

    void deleteUser(String email);

    void batchProcess() throws JsonProcessingException;

}
