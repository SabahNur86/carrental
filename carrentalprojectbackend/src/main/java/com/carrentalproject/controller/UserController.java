package com.carrentalproject.controller;

import com.carrentalproject.domain.User;
import com.carrentalproject.security.jwt.JwtUtils;
import com.carrentalproject.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RequestMapping
@Produces(MediaType.APPLICATION_JSON)
@RestController
@AllArgsConstructor
@CrossOrigin(origins="*",maxAge = 3600)
public class UserController {

    public UserService userService;
    public AuthenticationManager authenticationManager;//login olabilmek icin
    public JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<Map<String,Boolean>> registerUser(@Valid @RequestBody User user) {
        userService.register(user);
        Map<String, Boolean> map = new HashMap<>();//register oldugunda mesaj ve true donsun diye
        map.put("User registered successfully ", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> authenticateUser(@RequestBody Map<String,Object> userMap){
        //String,String olarak userName ve passwordu aldık bazen bu degiskenler object olarak alinmasi gerekir
        //o zaman (String) userMap.get ile casting yapilir
        String email=(String) userMap.get("email");
        String password=(String) userMap.get("password");

        userService.login(email,password);

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtUtils.generateJwtToken(authentication);

        Map<String,String> map=new HashMap<>();
        map.put("token",jwt);
        return new ResponseEntity<>(map,HttpStatus.OK);

    }

}
