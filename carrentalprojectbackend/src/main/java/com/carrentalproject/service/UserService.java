package com.carrentalproject.service;

import com.carrentalproject.domain.Role;
import com.carrentalproject.domain.User;
import com.carrentalproject.domain.enumeration.UserRole;
import com.carrentalproject.exception.AuthException;
import com.carrentalproject.exception.BadRequestException;
import com.carrentalproject.exception.ConflictException;
import com.carrentalproject.exception.ResourceNotFoundException;
import com.carrentalproject.repository.RoleRepository;
import com.carrentalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor //autowired kullanmaya gerek kalmasın diye
                    // ioc konteynırında olusturulan bean ı burda kullanmak icin
@Service
public class UserService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register (User user) throws BadRequestException{
        if(userRepository.existsByUserName(user.getUserName())){
            throw new ConflictException("Error: Username is already taken");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ConflictException("Error: Email is already in use");
        }
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Set<Role> roles= new HashSet<Role>();
            Role customerRole=roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                    .orElseThrow(()->new ResourceNotFoundException("Role is not found"));
        roles.add(customerRole);
        //Aynı userName e veya email adresine sahip kullanici yoksa
        //password u encode ederek rolunu veya rollerini listeye ekleyerek

        userRepository.save(user); // bu user bilgilerini table a kaydettik

    }

    public void login(String userName, String password){
        try{
            Optional<User> user=userRepository.findByUserName(userName);
            if(!BCrypt.checkpw(password, user.get().getPassword()))throw new AuthException("Invalid Credentials");
      //User classından olusturdugumuz user objesine bu metoda gonderilen userName in olup olmadıgını kontrol ederek
      // gonder ve checkpw metodu ile gelen passwordun userdaki password ile eşleşip eşleşmediğini kontrol et
            //yoksa exception fırlat
        }catch (Exception e){
            throw new AuthException("Invalid Credentials");
        }
    }

}
