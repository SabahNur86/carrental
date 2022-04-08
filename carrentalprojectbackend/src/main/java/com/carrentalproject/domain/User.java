package com.carrentalproject.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=15)
    @NotNull(message = "Please enter your first name")
    @Column(nullable = false,length=15)
    private String firstName;

    @Size(max=15)
    @NotNull(message = "Please enter your last name")
    @Column(nullable = false,length=15)
    private String lastName;


    @Size(min=4, max=60, message = "please enter min 4 char")
    @NotNull(message = "Please enter your password")
    @Column(nullable = false, length = 120)
    private String password;

    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",message = "Please enter valid phone number")
    @Size(min=14, max=14,message = "Phone number should be exact 10 chars")
    @NotNull(message = "Please enter your phone number")
    @Column(nullable = false,length = 14)
    private String phoneNumber;

    @Email
    @Size(min = 5,max = 150)
    @NotNull(message = "Please neter your email")
    @Column(nullable = false,unique = true,length = 150)
    private String email;


    @Size(max = 250)
    @NotNull(message = "Please enter your address")
    @Column(nullable = false,length = 250)
    private String address;

    @Size(max=8)
    @NotNull(message = "Please enter your zip code")
    @Column(nullable = false, length = 8)
    private String zipCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_roles" , //olusan tablonun adi
            joinColumns=@JoinColumn(name="user_id"), //primarykey (ana tablomuzdaki id)
             inverseJoinColumns = @JoinColumn(name="role_id")) //forignkey (baglanan tablodaki id)                   //yeni bir table olusturuyoruz adi
                         //Role class ımızdan bilgiler alıp bu class ımızdaki verilerle manytomany seklinde bagliyorum
                        //bir kisinin birden fazla rolü olabilecegi gibi bir role de birden fazla kisi sahip olabilir
    private Set<Role> roles=new HashSet<>();

    @Column(nullable = false)
    private Boolean builtIn;

}
