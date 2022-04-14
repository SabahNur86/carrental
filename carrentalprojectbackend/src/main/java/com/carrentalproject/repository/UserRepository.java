package com.carrentalproject.repository;

import com.carrentalproject.domain.User;
import com.carrentalproject.exception.BadRequestException;
import com.carrentalproject.exception.ConflictException;
import com.carrentalproject.exception.ResourceNotFoundException;
import com.carrentalproject.projection.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User,Long> {//bu repository nin user table ımıza ait oldugunu belirttik

    Optional<User> findByEmail(String email) throws ResourceNotFoundException;
    //username ile dataya ulasma sorgusu

    Boolean existsByEmail(String email) throws ConflictException;
    //User table ımızın icinde register olmaya calisan kisinin gonderdigi mail adresi var mı yok mu diye kontrol edecegimiz metod

    List<ProjectUser> findAllBy();

    @Modifying
    @Transactional
    @Query("UPDATE User u " +
            "SET u.firstName = ?2, u.lastName = ?3, u.phoneNumber = ?4, u.email = ?5, u.address = ?6, " +
            "u.zipCode = ?7 WHERE u.id = ?1")
    void update(Long id, String firstName, String lastName, String phoneNumber, String email, String address,
                String zipCode) throws BadRequestException;


}
