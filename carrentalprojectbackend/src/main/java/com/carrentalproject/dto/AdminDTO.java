package com.carrentalproject.dto;

import com.carrentalproject.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {

    @Size(max=15)
    @NotNull(message = "Please enter your first name")
    private String firstName;

    @Size(max=15)
    @NotNull(message = "Please enter your last name")
    private String lastName;


    @Size(min=4, max=60, message = "please enter min 4 char")
    private String password; //password u degistirmek zorunda kalmadan update olsun diye NotNull i kaldirdik

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",message = "Please enter valid phone number")
    @Size(min=14, max=14,message = "Phone number should be exact 10 chars")
    @NotNull(message = "Please enter your phone number")
    private String phoneNumber;

    @Email
    @Size(min = 5,max = 150)
    @NotNull(message = "Please neter your email")
    private String email;


    @Size(max = 250)
    @NotNull(message = "Please enter your address")
    private String address;

    @Size(max=8)
    @NotNull(message = "Please enter your zip code")
    private String zipCode;

    private Set<String> roles;

    private Boolean builtIn;
}
