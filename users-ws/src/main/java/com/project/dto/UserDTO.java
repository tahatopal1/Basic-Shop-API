package com.project.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, message = "First name must not be less than two characters")
    private String name;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, message = "Last name must not be less than two characters")
    private String surname;

    @NotNull(message = "Birth date cannot be null")
    private String birthDate;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
    private String password;

    @NotNull(message = "Email cannot be null")
    private String address;

    @NotNull(message = "Phone cannot be null")
    //@Size(min = 10, max = 10, message = "Phone must be 10 digits length")
    private String phone;

}
