package com.carrentalproject.domain;



import com.carrentalproject.domain.enumeration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    private Boolean builtIn; //backend'de update ve delete islemleri yapabilmemiz icin
                             // customer kendi passwordu vs. degisiklik yapabilmesi icin builtIn false olmalı true ise login olmamistir
                             // ya da login olmus fakat baska bir hesabin update veya delete islemlerinde sadece admin olması gerekir.
                             // bu metodlarda da customer in builtIn i true dur ve işlem yapamaz

    public Set<Role> getRole(){
        return roles;
    }

    public Set<String> getRoles() {
        Set<String> roles1 = new HashSet<>();
        Role [] role = roles.toArray(new Role[roles.size()]);

        for (int i = 0; i < roles.size(); i++){
            if(role[i].getName().equals(UserRole.ROLE_ADMIN))
                roles1.add("Administrator");
            else roles1.add("Customer");
        }
       return roles1;
    }//bu metodda user ın rollerini string bir ifade olarak gorebilmemiz icin once array e atarip
    // sonra set icinde listelemis olduk
}
