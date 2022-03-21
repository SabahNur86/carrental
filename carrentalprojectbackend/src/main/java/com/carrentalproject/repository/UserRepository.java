package com.carrentalproject.repository;

import com.carrentalproject.domain.Role;
import com.carrentalproject.domain.User;
import com.carrentalproject.domain.enumeration.UserRole;
import com.carrentalproject.exception.ConflictException;
import com.carrentalproject.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {//bu repository nin user table ımıza ait oldugunu belirttik

    Optional<User> findByUserName(String userName) throws ResourceNotFoundException;
    //username ile dataya ulasma sorgusu
    Boolean existsByUserName(String userName) throws ConflictException;
    //User table ımızın icinde register olmaya calisan kisinin gonderdigi username var mı yok mu diye kontrol edecegimiz metod
    Boolean existsByEmail(String email) throws ConflictException;
    //User table ımızın icinde register olmaya calisan kisinin gonderdigi mail adresi var mı yok mu diye kontrol edecegimiz metod


}
