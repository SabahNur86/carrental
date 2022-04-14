package com.carrentalproject.service;

import com.carrentalproject.domain.Role;
import com.carrentalproject.domain.User;
import com.carrentalproject.domain.enumeration.UserRole;
import com.carrentalproject.dto.UserDTO;
import com.carrentalproject.exception.AuthException;
import com.carrentalproject.exception.BadRequestException;
import com.carrentalproject.exception.ConflictException;
import com.carrentalproject.exception.ResourceNotFoundException;
import com.carrentalproject.projection.ProjectUser;
import com.carrentalproject.repository.RoleRepository;
import com.carrentalproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

//autowired kullanmaya gerek kalmasın diye
@AllArgsConstructor  // ioc konteynırında olusturulan bean ı burda kullanmak icin
@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final static String USER_NOT_FOUND_MSG="user with id %d not found";



    public void register (User user) throws BadRequestException{

        if(userRepository.existsByEmail(user.getEmail())){
            //register olmaya calisilan email daha once kullanilmis mi
            throw new ConflictException("Error: Email is already in use");
        }
        //kullanilmamissa passwordu encode edilmis password olaak kaydeder
        //builtIn
        String encodedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setBuiltIn(false);

        Set<Role> roles= new HashSet<>();
            Role customerRole=roleRepository.findByName(UserRole.ROLE_CUSTOMER)
                    .orElseThrow(()->new ResourceNotFoundException("Role is not found"));
        roles.add(customerRole);
        //Aynı userName e veya email adresine sahip kullanici yoksa
        //password u encode ederek rolunu veya rollerini listeye ekleyerek

        user.setRoles(roles);
        userRepository.save(user); // bu user bilgilerini table a kaydettik

    }

    public void login(String email, String password)throws AuthException{
        //database ile alakali beklmedigimiz bir hata olursa diye try catch arasina aliyoruz
        try{
            Optional<User> user=userRepository.findByEmail(email);
            if(!BCrypt.checkpw(password, user.get().getPassword()))
                throw new AuthException("Invalid Credentials");
      //User classindan olusturdugumuz user objesine bu metoda gonderilen email in olup olmadigini kontrol ederek
      // gonder ve checkpw metodu ile gelen passwordun userdaki password ile eslesip eslesmedigini kontrol et
            //yoksa exception firlat
        }catch (Exception e){
            throw new AuthException("Invalid Credentials");
        }
    }

    public UserDTO findById(Long id) throws ResourceNotFoundException{
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(USER_NOT_FOUND_MSG,id)));
        UserDTO userDTO=new UserDTO();
        userDTO.setRoles(user.getRole());
        return new UserDTO(user.getFirstName(),user.getLastName(),user.getPhoneNumber(),user.getEmail(),user.getAddress(),user.getZipCode(),userDTO.getRoles(),user.getBuiltIn());
    }
    public List<ProjectUser> fetchAllUsers(){
        return userRepository.findAllBy();
    }

    public void updateUser(Long id,UserDTO userDTO) throws BadRequestException{
        boolean emailExists= userRepository.existsByEmail((userDTO.getEmail()));
        Optional<User> userDetails=userRepository.findById(id);

        if(userDetails.get().getBuiltIn()){
            throw new BadRequestException("You dont have permission to update user info!");
        }
        if(emailExists && !userDTO.getEmail().equals(userDetails.get().getEmail())){
            throw new ConflictException("Error:Email is already in use");
        }
        userRepository.update(id, userDTO.getFirstName(),userDTO.getLastName(), userDTO.getPhoneNumber(),
                userDTO.getEmail(),userDTO.getAddress(),userDTO.getZipCode());
    }
}
