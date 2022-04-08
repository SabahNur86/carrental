package com.carrentalproject.repository;

import com.carrentalproject.domain.User;
import com.carrentalproject.exception.ConflictException;
import com.carrentalproject.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Long> {//bu repository nin user table ımıza ait oldugunu belirttik

    Optional<User> findByEmail(String email) throws ResourceNotFoundException;
    //username ile dataya ulasma sorgusu

    Boolean existsByEmail(String email) throws ConflictException;
    //User table ımızın icinde register olmaya calisan kisinin gonderdigi mail adresi var mı yok mu diye kontrol edecegimiz metod


}
