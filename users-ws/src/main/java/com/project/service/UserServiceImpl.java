package com.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.dto.RandomUserDTO;
import com.project.dto.UserDTO;
import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(user
                -> objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .setDateFormat(new SimpleDateFormat("dd-MM-yyyy"))
                        .convertValue(user, UserDTO.class))
                        .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(String email) {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new RuntimeException("User is not found"));
        return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setDateFormat(new SimpleDateFormat("dd-MM-yyyy"))
                .convertValue(user, UserDTO.class);
    }

    @Override
    public void createUser(UserDTO userDTO) {
        // Handle birth date
        Date birthDate = parseStringToDate(userDTO.getBirthDate());
        userDTO.setBirthDate(null);

        // Handle password
        String password = userDTO.getPassword();
        userDTO.setPassword(null);

        User user = objectMapper.convertValue(userDTO, User.class);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setBirthDate(birthDate);
        userRepository.save(user);
    }

    @Override
    public void updateUser(String email, UserDTO userDTO) {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new RuntimeException("User is not found"));
        mergeDTOInfo(userDTO, user);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(() -> new RuntimeException("User is not found"));
        userRepository.delete(user);
    }

    @Override
    public void batchProcess() throws JsonProcessingException {
        IntStream.range(1,50).forEach(value -> {
            try {
                createUserByAPI(fetchRandomUser());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createUserByAPI(RandomUserDTO randomUser) throws ParseException {
        User user = new User();

        // Name and Surname
        String[] nameSurname = randomUser.getName().split(" ");
        if (checkHasPrefix(randomUser.getName())){
            user.setName(nameSurname[1]);
            user.setSurname(nameSurname[2]);
        }else{
            user.setName(nameSurname[0]);
            user.setSurname(nameSurname[1]);
        }

        user.setId(UUID.randomUUID().toString());
        user.setAddress(randomUser.getAddress());
        user.setPhone(randomUser.getPhone_w());
        user.setEmail(randomUser.getEmail_u().concat(environment.getProperty("random_user.mail_extension")));
        user.setBirthDate(new SimpleDateFormat(environment.getProperty("random_user.date_format")).parse(randomUser.getBirth_data()));
        user.setPassword(bCryptPasswordEncoder.encode(environment.getProperty("random_user.password")));
        userRepository.save(user);
    }

    private RandomUserDTO fetchRandomUser() throws JsonProcessingException {
        ResponseEntity<String> response
                = restTemplate.exchange(environment.getProperty("random_user.url"), HttpMethod.GET, null, new ParameterizedTypeReference<String>() {});
        return objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(response.getBody(), RandomUserDTO.class);
    }

    private void mergeDTOInfo(UserDTO userDTO, User user) {
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setBirthDate(parseStringToDate(userDTO.getBirthDate()));
    }

    private Date parseStringToDate(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    private boolean checkHasPrefix(String str){
        return str.startsWith("Dr")
                || str.startsWith("Mrs")
                || str.startsWith("Miss")
                || str.startsWith("Ms")
                || str.startsWith("Prof");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true,
                true, true, true, new ArrayList<>());
    }
}
